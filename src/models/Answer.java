package models;

public class Answer{
    protected String AnswerText;
    protected Boolean IsValid;
    
    public Answer() {
        this.AnswerText = "";
        this.IsValid = Boolean.FALSE;
    }
    public Boolean getCorrection() {return IsValid;}
    public String getText() {return AnswerText;}
    public String getAnswerXML(){
        String r = "";
        r += "<Answer valid=\"" + IsValid.toString() + "\">" + AnswerText + "</Answer>";
        return r;
    }
    public static Answer toAnswer(String xml) {
        Answer r = new Answer();
        if(xml.contains("valid=\"true\"")) r.IsValid = Boolean.TRUE;
        else r.IsValid = Boolean.FALSE;
        int begIndex = xml.indexOf(">") + 1;
        int endIndex = xml.indexOf("</Answer");
        r.AnswerText = xml.substring(begIndex, endIndex);
        return r;
    }
}


