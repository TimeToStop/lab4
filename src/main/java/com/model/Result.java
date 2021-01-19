package com.model;

import com.utils.json.JsonObject;
import com.utils.json.Jsonable;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "results")
public class Result implements Jsonable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "x")
    private double x;

    @Column(name = "y")
    private double y;

    @Column(name = "r")
    private double r;

    @Column(name = "result")
    private String answer;

    @Column(name = "time")
    private String time;

    @Column(name = "time_executed")
    private long workTime;

    public Result()
    {
        this.answer = "";
        this.time = "";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getWorkTime() {
        return workTime;
    }

    public void setWorkTime(long workTime) {
        this.workTime = workTime;
    }

    public Result(double x, double y, double r,
                  String answer, String time, long workTime, long userId )
    {
        this.x = x;
        this.y = y;
        this.r = r;
        this.answer = answer;
        this.time = time;
        this.workTime = workTime;
        this.userId = userId;
    }

    @Override
    public String toJson()
    {
        JsonObject object = new JsonObject();

        object.addProperty("x", x);
        object.addProperty("y", y);
        object.addProperty("r", r);
        object.addProperty("result", answer.equals("true"));
        object.addProperty("time", time);
        object.addProperty("time_executed", workTime);

        return object.toJson();
    }
}
