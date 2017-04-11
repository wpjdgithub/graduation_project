package Grad.Service.searchservice.lucene;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import Grad.Service.wenshu.Wenshu;
public class IndexingFiles {
	private List<Wenshu> list;
	public IndexingFiles(List<Wenshu> list){
		this.list = list;
	}
	public void index() throws IOException{
		Analyzer analyzer = new StandardAnalyzer();//建立一个词法分析器
		Path indexPath = Paths.get("luceneIndex/index1.0");
		Directory directory = FSDirectory.open(indexPath);
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter indexWriter = new IndexWriter(directory,config);
		int size = this.list.size();
		for(int i = 0;i < size;i++){
			Document document = new Document();
			Wenshu wenshu = this.list.get(i);
			document.add(new Field("filepath",wenshu.getFilepath(),TextField.TYPE_STORED));
			document.add(new Field("casebrief",wenshu.getCaseBrief(),TextField.TYPE_STORED));
			document.add(new Field("caseid",wenshu.getCaseID(),TextField.TYPE_STORED));
			document.add(new Field("caseprogram",wenshu.getCaseProgram(),TextField.TYPE_STORED));
			document.add(new Field("casetype",wenshu.getCaseType(),TextField.TYPE_STORED));
			document.add(new Field("caseyear",wenshu.getCaseYear(),TextField.TYPE_STORED));
			document.add(new Field("courtarea",wenshu.getCourtArea(),TextField.TYPE_STORED));
			document.add(new Field("courtlevel",wenshu.getCourtLevel(),TextField.TYPE_STORED));
			document.add(new Field("courtname",wenshu.getCourtName(),TextField.TYPE_STORED));
			document.add(new Field("documentname",wenshu.getDocumentName(),TextField.TYPE_STORED));
			document.add(new Field("documenttype",wenshu.getDocumentType(),TextField.TYPE_STORED));
			document.add(new Field("fulltext",wenshu.getFullText(),TextField.TYPE_STORED));
			document.add(new Field("keywords",wenshu.getKeywords(),TextField.TYPE_STORED));
			document.add(new Field("participants",wenshu.getParticipantInfoString(),TextField.TYPE_STORED));
			document.add(new Field("judgers",wenshu.getJudgerInfoString(),TextField.TYPE_STORED));
			document.add(new Field("laws",wenshu.getLawsString(),TextField.TYPE_STORED));
			indexWriter.addDocument(document);
		}
		indexWriter.close();
		directory.close();
	}
}
