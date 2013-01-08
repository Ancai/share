/**
 * 
 */
package com.share.controller;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 控制器：Lucene功能试验
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-11-26 下午3:51:07
 * @version 1.0
 */
@Controller
@RequestMapping("/lucene")
public class LuceneDo {
	/**
	 * 添加索引
	 */
	public String addIndex() {
		Directory dir = new RAMDirectory();
		IndexWriterConfig writerConfig = new IndexWriterConfig(Version.LUCENE_40, new StandardAnalyzer(Version.LUCENE_40));
		IndexWriter indexWriter = null;
		try {
			indexWriter = new IndexWriter(dir, writerConfig);
			Document doc = new Document();
			//Field field = new Field();
			//doc.add(new Field("title", "lucene introduction", Field.Store.YES, Field.Index.TOKENIZED)); 
			doc.add(new Field("title", "lucene introduction", Field.Store.YES, Field.Index.ANALYZED));
			indexWriter.addDocument(doc);
			indexWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}
}
