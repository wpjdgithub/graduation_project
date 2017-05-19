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
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import Grad.Service.wenshu.Wenshu;
import Grad.Service.xml.WenshuXMLObject;
public class SearchFiles {
	private Analyzer analyzer;
	private Path indexPath;
	private Directory directory;
    private DirectoryReader ireader;
    private IndexSearcher isearcher;
    private String path = "F:\\Programming.Project\\GitRepo\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\graduation_project\\";
	public SearchFiles(){
		this.analyzer = new StandardAnalyzer();
		this.indexPath = Paths.get(path+"luceneIndex\\index1.0");
		try {
			this.directory = FSDirectory.open(indexPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    try {
			this.ireader = DirectoryReader.open(directory);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    this.isearcher = new IndexSearcher(ireader);
	}
	//For test
	public SearchFiles(String path){
		this.path = path;
		this.analyzer = new StandardAnalyzer();
		this.indexPath = Paths.get(path+"luceneIndex\\index1.0");
		try {
			this.directory = FSDirectory.open(indexPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    try {
			this.ireader = DirectoryReader.open(directory);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    this.isearcher = new IndexSearcher(ireader);
	}
	public List<Wenshu> search(SearchItem searchItem,String value) throws IOException, ParseException{
		String key = searchItem.toString();
		List<Wenshu> list = new ArrayList<Wenshu>();
	    QueryParser parser = new QueryParser(key, analyzer);
	    Query query = parser.parse(value);
	    ScoreDoc[] hits = isearcher.search(query, 100).scoreDocs;
	    for (int i = 0; i < hits.length; i++) {
	    	Document hitDoc = isearcher.doc(hits[i].doc);
	    	String filepath = hitDoc.get("filepath");
	    	WenshuXMLObject wenshuXML = new WenshuXMLObject(filepath);
	    	Wenshu wenshu = wenshuXML.toWenshu();
	    	list.add(wenshu);
	    }
	    return list;
	}
	public List<Wenshu> search(List<SearchItem> items,List<String> values) throws IOException, ParseException{
		List<Wenshu> result = new ArrayList<Wenshu>();
		int itemSize = items.size();
		int valueSize = values.size();
		String[] item = new String[itemSize];
		String[] value = new String[valueSize];
		for(int i = 0;i < itemSize;i++){
			item[i] = items.get(i).toString();
		}
		for(int j = 0;j < valueSize;j++){
			value[j] = values.get(j);
		}
		//避免出现参数错误
		int size = itemSize < valueSize ? itemSize:valueSize;
		BooleanClause.Occur[] clauses = new BooleanClause.Occur[size];
		for(int k = 0;k < size;k++){
			clauses[k] = BooleanClause.Occur.MUST;
		}
		Query query = MultiFieldQueryParser.parse(value, item, clauses, this.analyzer);
		ScoreDoc[] hits = this.isearcher.search(query, 100).scoreDocs;
		for(int i = 0;i < hits.length;i++){
			Document hitDoc = this.isearcher.doc(hits[i].doc);
			String filepath = hitDoc.get("filepath");
			WenshuXMLObject wenshuXML = new WenshuXMLObject(this.path+filepath);
			Wenshu wenshu = wenshuXML.toWenshu();
			result.add(wenshu);
		}
		return result;
	}
	public Wenshu search(String caseID) throws IOException, ParseException{
		QueryParser parser = new QueryParser(SearchItem.caseid.toString(),analyzer);
		Query query = parser.parse(caseID);
		ScoreDoc[] hits = isearcher.search(query,1).scoreDocs;
		if(hits == null || hits.length == 0)
			return null;
		else{
			Document hitDoc = isearcher.doc(hits[0].doc);
			String filepath = hitDoc.get("filepath");
			WenshuXMLObject wenshuXML = new WenshuXMLObject(this.path+filepath);
			Wenshu wenshu = wenshuXML.toWenshu();
			return wenshu;
		}
	}
	//Test
	public static void main(String[] args) throws IOException, ParseException{
		SearchFiles searchFiles = new SearchFiles();
		Wenshu wenshu = searchFiles.search("(2013)南刑初字第21号刑事判决书（一审公诉案件适用普通程序用）.doc.xml");
		System.out.println(wenshu.getCaseBrief());
	}
	public void close() throws IOException{
	    ireader.close();
	    directory.close();
	}
}