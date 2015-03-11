package tastery.datamanager;

import java.util.List;

import rj.hive.database.models.CartMap;
import rj.hive.tastery.R.id;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TasteryCartAdapter extends ArrayAdapter<CartMap> {
	
	private LayoutInflater mInflater;
	private List<CartMap> cartItems;
	private int mViewResourceId;
	private String l, aType;
	private Context adapterContext;

	public TasteryCartAdapter(Context context, int resource,
			List<CartMap> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		
		this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.cartItems = objects; 
		this.mViewResourceId = resource;
		this.adapterContext = context;
	}
	
	@Override
    public int getCount() {
        return this.cartItems.size();
    }

    @Override
    public CartMap getItem(int position) {
        return this.cartItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);
        CartMap cartItem = this.cartItems.get(position);
        
        TextView txtOrderDate = (TextView)convertView.findViewById(id.txtOrderDate);
        TextView txtItem = (TextView)convertView.findViewById(id.txtItem);
        EditText txtQuantity = (EditText)convertView.findViewById(id.txtQuantity);
        EditText txtTime = (EditText)convertView.findViewById(id.txtTime);
        TextView txtPrice = (TextView)convertView.findViewById(id.txtPrice);
        
        txtOrderDate.setText(cartItem.order_date);
        txtItem.setText(Html.fromHtml(cartItem.name));
        txtQuantity.setText(""+cartItem.quantity);
        txtTime.setText(cartItem.deliver_time);
        txtPrice.setText(cartItem.price);
        
        txtOrderDate.setTextColor(Color.DKGRAY);
        txtItem.setTextColor(Color.DKGRAY);
        txtQuantity.setTextColor(Color.DKGRAY);
        txtTime.setTextColor(Color.DKGRAY);
        txtPrice.setTextColor(Color.DKGRAY);
        
        // ImageView imgMenuThumbnail = (ImageView)convertView.findViewById(R.id.imgMenuThumbnail);
        // new DownloadImageTask(imgMenuThumbnail).execute(product.thumb);
        
        return convertView;
    }
    
}
