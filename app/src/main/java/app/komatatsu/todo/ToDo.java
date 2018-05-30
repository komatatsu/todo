package app.komatatsu.todo;

import com.google.gson.annotations.SerializedName;

public class ToDo {
    @SerializedName("id")
    private long id;
    @SerializedName("title")
    private String title;
    @SerializedName("check")
    private boolean check;

    public ToDo(long l, String s, boolean b) {
        this.id = l;
        this.title = s;
        this.check = b;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
