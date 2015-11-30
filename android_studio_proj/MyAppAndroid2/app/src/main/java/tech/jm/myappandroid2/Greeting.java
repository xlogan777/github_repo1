package tech.jm.myappandroid2;

/**
 * Created by jimmy on 11/27/2015.
 */
public class Greeting
{
    private String id;
    private String content;

    public String getId() {
        return this.id;
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public String toString(){
        return "Greeting{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}