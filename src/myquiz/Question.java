package myquiz;

class Question {

    String statement;
    String option[] = new String[5];
    int indexOfCorrectAns;
    boolean isUsed = false;

    public Question() {
        statement = "";
        for (int i = 0; i < 5; i++) {
            option[i] = "";
        }
        indexOfCorrectAns = 0;
    }

    public Question(String statement, String[] option, int indexOfCorrectAns) {
        this.statement = statement;
        this.option = option;
        this.indexOfCorrectAns = indexOfCorrectAns;
    }

    public Question(String statement, String option1, String option2, String option3, String option4, String option5, int indexOfCorrectAns) {
        this.statement = statement;
        option[0] = option1;
        option[1] = option2;
        option[2] = option3;
        option[3] = option4;
        option[4] = option5;
        this.indexOfCorrectAns = indexOfCorrectAns;
    }

    public void setStatement(String s) {
        statement = s;
    }

    public void setOption(String[] o) {
        option = o;
    }

    public void setIndexOfCorrectAns(int i) {
        indexOfCorrectAns = i;
    }

    public void setIsUsed(boolean b) {
        isUsed = b;
    }

    public String getStatement() {
        return statement;
    }

    public String[] getOptions() {
        return option;
    }

    public int getIndexOfCorrectAns() {
        return indexOfCorrectAns;
    }

    public boolean isUsed() {
        return isUsed;
    }
}
