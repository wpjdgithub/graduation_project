package Grad.Service.searchservice.lucene;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import Grad.Service.wenshu.Wenshu;
import Grad.Service.xml.WenshuXMLObject;

public class SearchFiles {
	public SearchFiles(){
		
	}
	public List<Wenshu> search(String key,String value) throws IOException, ParseException{
		List<Wenshu> list = new ArrayList<Wenshu>();
		Analyzer analyzer = new StandardAnalyzer();
		Path indexPath = Paths.get("luceneIndex/index1.0");
		Directory directory = FSDirectory.open(indexPath);
	    DirectoryReader ireader = DirectoryReader.open(directory);
	    IndexSearcher isearcher = new IndexSearcher(ireader);
	    QueryParser parser = new QueryParser(key, analyzer);
	    Query query = parser.parse(value);
	    ScoreDoc[] hits = isearcher.search(query, 1000).scoreDocs;//TODO:先来1000个，效果不好后续再改
	    // Iterate through the results:
	    for (int i = 0; i < hits.length; i++) {
	    	Document hitDoc = isearcher.doc(hits[i].doc);
	    	String filepath = hitDoc.get("filepath");
	    	System.out.println(filepath);
	    	WenshuXMLObject wenshuXML = new WenshuXMLObject(filepath);
	    	Wenshu wenshu = wenshuXML.toWenshu();
	    	list.add(wenshu);
	    }
	    ireader.close();
	    directory.close();
	    return list;
	}
	public static void main(String[] args) throws IOException, ParseException{
		String key = "keywords";
		String value = "有期徒刑";
		SearchFiles searchFiles = new SearchFiles();
		List<Wenshu> list = searchFiles.search(key, value);
		System.out.println(list.size());
	}
}
