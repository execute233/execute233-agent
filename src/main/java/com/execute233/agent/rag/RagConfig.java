package com.execute233.agent.rag;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.document.parser.apache.poi.ApachePoiDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author execute233
 * @date 2026/3/29
 **/
// 加载RAG
@Configuration
public class RagConfig {
    @Autowired
    private EmbeddingModel embeddingModel;
    @Autowired
    private EmbeddingStore<TextSegment> embeddingStore;
    @Bean
    public ContentRetriever contentRetriever() {
        // 加载文档
        Document document = ClassPathDocumentLoader.loadDocument("docs.docx", new ApachePoiDocumentParser());
        // 文档切割：按照段落分割，最大1000个字符，最多重叠200个字符
        DocumentByParagraphSplitter splitter = new DocumentByParagraphSplitter(1000, 200);
        // 自定义文档加载器，文档转为向量保存在数据库中
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(splitter)
                // 为了提高文档的质量，为每个切割后的文档碎片 TextSegment 添加文档名称作为元信息
                .textSegmentTransformer(textSegment ->
                        TextSegment.from(textSegment.metadata().getString("file_name") + "\n" + textSegment.text(),
                                textSegment.metadata()))
                // 指定使用的向量模型
                .embeddingModel(embeddingModel)
                // 向量存储，这里用内存存储
                .embeddingStore(embeddingStore)
                .build();
        // 加载文档
        ingestor.ingest(document);
        // 自定义内容加载器
        EmbeddingStoreContentRetriever retriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(5) // 最多5条结果
                .minScore(0.75) // 过滤分数小于0.75的结果
                .build();
        return retriever;
    }
}
