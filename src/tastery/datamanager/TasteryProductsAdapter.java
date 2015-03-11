package tastery.datamanager;

import java.io.InputStream;
import java.util.List;

import rj.hive.database.models.Product;
import rj.hive.tastery.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class TasteryProductsAdapter extends ArrayAdapter<Product> {
	
	private LayoutInflater mInflater;
	private List<Product> products;
	private int mViewResourceId;
	private String l, aType;

	public TasteryProductsAdapter(Context context, int resource,
			List<Product> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		
		this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.products = objects; 
		this.mViewResourceId = resource;
	}
	
	@Override
    public int getCount() {
        return this.products.size();
    }

    @Override
    public Product getItem(int position) {
        return this.products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);
        Product product = this.products.get(position);
        
        ImageView imgMenuThumbnail = (ImageView)convertView.findViewById(R.id.imgMenuThumbnail);
        new DownloadImageTask(imgMenuThumbnail).execute(product.thumb);
        
        return convertView;
    }
    
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    	  ImageView bmImage;

    	  public DownloadImageTask(ImageView bmImage) {
    	      this.bmImage = bmImage;
    	  }

    	  protected Bitmap doInBackground(String... urls) {
    	      String urldisplay = urls[0];
    	      urldisplay = urldisplay.replace(" ", "%20");
    	      Bitmap mIcon11 = null;
    	      try {
    	        InputStream in = new java.net.URL(urldisplay).openStream();
    	        mIcon11 = BitmapFactory.decodeStream(in);
    	      } catch (Exception e) {
    	          Log.e("Error", e.getMessage());
    	          e.printStackTrace();
    	      }
    	      return mIcon11;
    	  }

    	  protected void onPostExecute(Bitmap result) {
    	      bmImage.setImageBitmap(result);
    	  }
    }

}
