package com.luoyp.brnmall.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyp3314@gmail.com on 16/4/14.
 */
public class CategoryModel {

    private String result;

    @SerializedName("data")
    private List<Category> category;

    public static CategoryModel objectFromData(String str) {

        return new Gson().fromJson(str, CategoryModel.class);
    }

    public static CategoryModel objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), CategoryModel.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<CategoryModel> arrayCategoryModelFromData(String str) {

        Type listType = new TypeToken<ArrayList<CategoryModel>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<CategoryModel> arrayCategoryModelFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<CategoryModel>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public static class Category {
        private int cateId;
        private String cateName;

        public static Category objectFromData(String str) {

            return new Gson().fromJson(str, Category.class);
        }

        public static Category objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), Category.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<Category> arrayCategoryFromData(String str) {

            Type listType = new TypeToken<ArrayList<Category>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<Category> arrayCategoryFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<Category>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public int getCateId() {
            return cateId;
        }

        public void setCateId(int cateId) {
            this.cateId = cateId;
        }

        public String getCateName() {
            return cateName;
        }

        public void setCateName(String cateName) {
            this.cateName = cateName;
        }
    }
}
