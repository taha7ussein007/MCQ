package models;


public class Question {
    public String QuestionText;
    public Answer[] AnswersList;
    public Integer ID;
    
    public Question(Integer t){
        AnswersList = new Answer[4];
        for(int i = 0; i < AnswersList.length; i++) {
            AnswersList[i] = new Answer();
        }
        ID = t;
    }
    public Boolean setAnswer(String ansText, Integer ansIndex, Boolean isValid) {
        if(ansText.equals("")) return Boolean.FALSE;
        try {
            this.AnswersList[ansIndex].AnswerText = ansText;
            this.AnswersList[ansIndex].IsValid = isValid;
        }
        catch(ArrayIndexOutOfBoundsException | NullPointerException arg) 
        {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
    public Boolean isValidQuestion() {
        Boolean a = (  this.AnswersList[0].IsValid 
                    || this.AnswersList[1].IsValid
                    || this.AnswersList[2].IsValid 
                    || this.AnswersList[3].IsValid);
        Boolean b = (this.AnswersList[0].AnswerText != null 
                    && this.AnswersList[1].AnswerText != null
                    && this.AnswersList[2].AnswerText != null 
                    && this.AnswersList[3].AnswerText != null);
        Boolean c = this.QuestionText != null && this.ID >= 0;
        return (a && b && c);
    }
    public String toXML() {
        String a1 = AnswersList[0].getAnswerXML() + '\n';
        String a2 = AnswersList[1].getAnswerXML() + '\n';
        String a3 = AnswersList[2].getAnswerXML() + '\n';
        String a4 = AnswersList[3].getAnswerXML() + '\n';
         String q;
         q = "<QuestionText>" + this.QuestionText + "</QuestionText>";
         String r;
         r = "<Question id=\"" + ID.toString() + "\">" + '\n' +q + '\n' + a1 + a2  + a3 + a4 + "</Question>" + '\n';
         return r;
    }
    public static Question toQuestion(String xml){
        Question x = new Question(-1);
        String v = "<Question id=\"";
        String y = "<QuestionText>";
        String z = "</QuestionText>";
        int begIndex = xml.indexOf(v) + v.length();
        int endIndex = xml.indexOf("\">");
        try {
            x.ID = Integer.parseInt(xml.substring(begIndex,endIndex));
        }catch(Exception e) {
            System.out.println("Error");return null;}
        begIndex = xml.indexOf(y) + y.length();
        endIndex = xml.indexOf(z);
        x.QuestionText = xml.substring(begIndex,endIndex);
        for(int i = 0; i < 4; i++) x.AnswersList[i] = Answer.toAnswer(ansList(xml)[i]);
        return x;
    }
    private static String[] ansList(String xml) {
        int bpos = xml.indexOf("<Answer valid=");
        int epos = xml.indexOf("</Question>");
        String t = xml.substring(bpos, epos);
        return t.split("\n");
    }
    
    public String toPDFString() {
        String a = "{  } " + this.AnswersList[0].AnswerText;
        String b = "{  } " + this.AnswersList[1].AnswerText;
        String c = "{  } " + this.AnswersList[2].AnswerText;
        String d = "{  } " + this.AnswersList[3].AnswerText;
        String m = this.QuestionText;
        String r = m +  "\n" + a + "     \n" + b + "    \n" + c + "     \n" + d + "\n\n";
        return r;
    }
}
