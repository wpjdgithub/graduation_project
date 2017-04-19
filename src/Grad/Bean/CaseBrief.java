package Grad.Bean;

import java.io.Serializable;

public class CaseBrief implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3731415786746700236L;
	private String id;
	private String title;
	private String court;
	private String date;
	private String brief; // 案由
	private String process_judgement; // 审判程序
	private String type_text; // 文书类型
	private String source; //来源
	private String core; // 文书核心词汇
	
	public CaseBrief(){}

	public CaseBrief(String id,String title, String court, String date, String brief, String process_judgement, String type_text,
			String source, String core) {
		super();
		this.id = id;
		this.title = title;
		this.court = court;
		this.date = date;
		this.brief = brief;
		this.process_judgement = process_judgement;
		this.type_text = type_text;
		this.source = source;
		this.core = core;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCourt() {
		return court;
	}

	public void setCourt(String court) {
		this.court = court;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getProcess_judgement() {
		return process_judgement;
	}

	public void setProcess_judgement(String process_judgement) {
		this.process_judgement = process_judgement;
	}

	public String getType_text() {
		return type_text;
	}

	public void setType_text(String type_text) {
		this.type_text = type_text;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCore() {
		return core;
	}

	public void setCore(String core) {
		this.core = core;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
