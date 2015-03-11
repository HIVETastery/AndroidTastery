package rj.hive.asynctasks.transporter;

import java.util.ArrayList;

import org.json.JSONObject;

import rj.hive.enums.URLType;


import android.content.Context;

public class JSONTransporter 
{
 boolean errorThrown = false;
 public JSONTransporter(Context context, URLType urltype, ArrayList<Object> objects, JSONObject object)
 {
 transport(context, urltype, objects, object);
 }

 public JSONTransporter(Context context, URLType urltype, ArrayList<Object> objects, JSONObject object, boolean errorThrown)
 {
 this.errorThrown = errorThrown;
 transport(context, urltype, objects, object);
 } 
 
 private void transport(Context context, URLType urltype, ArrayList<Object> objects, JSONObject object) 
 {
 switch(urltype)
 {


 }
 }
}
