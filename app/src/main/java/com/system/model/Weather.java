package com.system.model;

/**
 * 作者：凌涛 on 2019/1/27 16:22
 * 邮箱：771548229@qq..com
 */
public class Weather {

    /**
     * temp : 22
     * windDirection : 西南风
     * windStrength : 1级
     * humidity : 35%
     * time : 16:03
     * temperature : 13℃~23℃
     * weather : 晴
     * weatherId : {"fa":"00","fb":"00"}
     * wind : 持续无风向微风
     * week : 星期日
     * city : 番禺
     * date : 2019年01月27日
     * dressingIndex : 舒适
     * dressingAdvice : 建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。
     * uvIndex : 中等
     * washIndex : 较适宜
     * travelIndex : 较适宜
     * exerciseIndex : 较适宜
     * dryingIndex :
     */

    private String temp;
    private String windDirection;
    private String windStrength;
    private String humidity;
    private String time;
    private String temperature;
    private String weather;
    private WeatherIdBean weatherId;
    private String wind;
    private String week;
    private String city;
    private String date;
    private String dressingIndex;
    private String dressingAdvice;
    private String uvIndex;
    private String washIndex;
    private String travelIndex;
    private String exerciseIndex;
    private String dryingIndex;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindStrength() {
        return windStrength;
    }

    public void setWindStrength(String windStrength) {
        this.windStrength = windStrength;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public WeatherIdBean getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(WeatherIdBean weatherId) {
        this.weatherId = weatherId;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDressingIndex() {
        return dressingIndex;
    }

    public void setDressingIndex(String dressingIndex) {
        this.dressingIndex = dressingIndex;
    }

    public String getDressingAdvice() {
        return dressingAdvice;
    }

    public void setDressingAdvice(String dressingAdvice) {
        this.dressingAdvice = dressingAdvice;
    }

    public String getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(String uvIndex) {
        this.uvIndex = uvIndex;
    }

    public String getWashIndex() {
        return washIndex;
    }

    public void setWashIndex(String washIndex) {
        this.washIndex = washIndex;
    }

    public String getTravelIndex() {
        return travelIndex;
    }

    public void setTravelIndex(String travelIndex) {
        this.travelIndex = travelIndex;
    }

    public String getExerciseIndex() {
        return exerciseIndex;
    }

    public void setExerciseIndex(String exerciseIndex) {
        this.exerciseIndex = exerciseIndex;
    }

    public String getDryingIndex() {
        return dryingIndex;
    }

    public void setDryingIndex(String dryingIndex) {
        this.dryingIndex = dryingIndex;
    }

    public static class WeatherIdBean {
        /**
         * fa : 00
         * fb : 00
         */

        private String fa;
        private String fb;

        public String getFa() {
            return fa;
        }

        public void setFa(String fa) {
            this.fa = fa;
        }

        public String getFb() {
            return fb;
        }

        public void setFb(String fb) {
            this.fb = fb;
        }
    }
}
