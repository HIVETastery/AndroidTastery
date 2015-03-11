package info.androidhive.iconchanger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.widget.Button;

public class DecoratedIconBitmap
{

	public BitmapDrawable mBitmap;
	final int MAX = 99; 
	final String MEASUREHOLDER = "121";
	final int CIRCLECOLOR = 0xffff0000;//005375;//A variation of orange
	final int TEXTCOLOR   = Color.WHITE;
	final String VALUE;
	
	public DecoratedIconBitmap(Context context, int drawable, int value)
	{
		 VALUE  = (value > MAX)? MAX + "+": value + "";
	     Rect bounds = new Rect(), shortText = new Rect();
	     
	        Paint paint = new Paint(); 
	        paint.setStyle(Style.FILL);  
	        paint.setColor(TEXTCOLOR); 
	        paint.setTextSize(new Button(context).getTextSize()); 
	        
	        paint.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/brandon/Brandon_reg.otf"));

			Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaint.setColor(CIRCLECOLOR);

			Bitmap bitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), drawable));
			bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
			Canvas mCanvas = new Canvas(bitmap);
			
			paint.getTextBounds( MEASUREHOLDER, 0, (MEASUREHOLDER).length(), bounds);
			paint.getTextBounds(VALUE, 0, VALUE.length(), shortText);
			
			float height = bounds.height();	
			float width  = paint.measureText(MEASUREHOLDER);
			
			float circleHypotenuse = (float) (Math.sqrt((width*width) + (height* height))/2);
		
			mCanvas.drawCircle(circleHypotenuse,circleHypotenuse,circleHypotenuse,mPaint);
	        
	        mCanvas.drawText(VALUE, (circleHypotenuse - shortText.width()/2), (circleHypotenuse - shortText.height()/2) + height, paint);
	        
	        mBitmap = new BitmapDrawable(context.getResources(), bitmap);
	}
}
