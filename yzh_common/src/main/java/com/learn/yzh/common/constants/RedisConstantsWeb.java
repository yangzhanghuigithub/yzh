package com.learn.yzh.common.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * redis的key
 * ClassName:RedisConstants
 *
 * @author yw
 * @create 2017-11-16 11:30
 */
@Component
public class RedisConstantsWeb {
    @Value("${key.hall}")
    private String hall;//影厅key
    @Value("${key.seat}")
    private String seat;//座位
    @Value("${key.filmlang}")
    private String filmlang;
    @Value("${key.filmsight}")
    private String filmsight;
    @Value("${key.showinfo}")
    private String showinfo;
    @Value("${key.showeventprice}")
    private String showeventprice;
    @Value("${key.showeventpriceStrategyPrice}")
    private String showeventpriceStrategyPrice;
    @Value("${key.showeventpriceStrategyServicefee}")
    private String showeventpriceStrategyServicefee;
    @Value("${key.show}")
    private String show;//场次 hash存放
    @Value("${key.filmsinfo}")
    private String filmsinfo;
    @Value("${key.filmskeyinfo}")
    private String filmskeyinfo;
    @Value("${key.filmsnotshow}")
    private String filmsnotshow;
    @Value("${key.token}")
    private String token;//登陆token
    @Value("${key.sections}")
    private String sections;
    @Value("${key.dict}")
    private String dict;
    @Value("${key.eventTime}")
    private String eventTime;

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getFilmlang() {
        return filmlang;
    }

    public void setFilmlang(String filmlang) {
        this.filmlang = filmlang;
    }

    public String getFilmsight() {
        return filmsight;
    }

    public void setFilmsight(String filmsight) {
        this.filmsight = filmsight;
    }

    public String getShowinfo() {
        return showinfo;
    }

    public void setShowinfo(String showinfo) {
        this.showinfo = showinfo;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getFilmsinfo() {
        return filmsinfo;
    }

    public void setFilmsinfo(String filmsinfo) {
        this.filmsinfo = filmsinfo;
    }

    public String getFilmsnotshow() {
        return filmsnotshow;
    }

    public void setFilmsnotshow(String filmsnotshow) {
        this.filmsnotshow = filmsnotshow;
    }

    public String getDict() {
        return dict;
    }

    public void setDict(String dict) {
        this.dict = dict;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSections() {
        return sections;
    }

    public void setSections(String sections) {
        this.sections = sections;
    }

    public String getShoweventprice() {
        return showeventprice;
    }

    public void setShoweventprice(String showeventprice) {
        this.showeventprice = showeventprice;
    }

    public String getShoweventpriceStrategyPrice() {
        return showeventpriceStrategyPrice;
    }

    public void setShoweventpriceStrategyPrice(String showeventpriceStrategyPrice) {
        this.showeventpriceStrategyPrice = showeventpriceStrategyPrice;
    }

    public String getShoweventpriceStrategyServicefee() {
        return showeventpriceStrategyServicefee;
    }

    public void setShoweventpriceStrategyServicefee(String showeventpriceStrategyServicefee) {
        this.showeventpriceStrategyServicefee = showeventpriceStrategyServicefee;
    }

    public String getFilmskeyinfo() {
        return filmskeyinfo;
    }

    public void setFilmskeyinfo(String filmskeyinfo) {
        this.filmskeyinfo = filmskeyinfo;
    }
}
