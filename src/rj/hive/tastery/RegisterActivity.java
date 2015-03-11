package rj.hive.tastery; 
import tastery.datamanager.TasteryAPIManager;
import tastery.datamanager.TasteryAPIManager.OnRequestComplete;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity { 
	public EditText firstName; 
	public EditText lastName; 
	public EditText email; 
	public EditText telephone; 
	public EditText mobile; 
	public EditText company; 
	public EditText address1; 
	public EditText address2; 
	public EditText city; 
	public EditText password1; 
	public EditText password2; 
	public ScrollView scrollView; 
	
	public final void focusOnView(final View v) { 
		new Handler().post(new Runnable() { 
			@Override public void run() { 
				int vTop = v.getTop(); 
				int vBottom = v.getBottom(); 
				int height = v.getHeight(); 
				if(scrollView != null) 
					scrollView.smoothScrollTo(0, ((vTop + vBottom) / 2) - (height)); 
				} 
			}); 
		} 
	
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.fragment_registration_form); 
		int titleId = getResources().getIdentifier("action_bar_title", "id","android"); 
		
		TextView yourTextView = (TextView) findViewById(titleId); 
		yourTextView.setTextAppearance(this, android.R.style.TextAppearance_Large); 
		yourTextView.setTypeface( Typeface.createFromAsset(getAssets(), "fonts/gearb/GearedSlab-Light.ttf")); 
		yourTextView.setTextColor(getResources().getColor(R.color.white)); 
		
		scrollView = (ScrollView)findViewById(R.id.scrollView); 
		firstName = (EditText)findViewById(R.id.editText1); 
		lastName = (EditText)findViewById(R.id.editText2); 
		email = (EditText)findViewById(R.id.editText3); 
		telephone = (EditText)findViewById(R.id.editText4); 
		mobile = (EditText)findViewById(R.id.editText5); 
		company = (EditText)findViewById(R.id.company); 
		address1 = (EditText)findViewById(R.id.editText6); 
		address2 = (EditText)findViewById(R.id.editText7); 
		city = (EditText)findViewById(R.id.editText8); 
		password1 = (EditText)findViewById(R.id.passwordText1); 
		password2 = (EditText)findViewById(R.id.passwordText2); 
		
		TextView terms = (TextView)findViewById(R.id.checkbox1); 
		final Button allow = (Button)findViewById(R.id.allow); 
		final Button deny = (Button)findViewById(R.id.deny); 
		allow.setOnClickListener(new OnClickListener(){ 
			@Override 
			public void onClick(View arg0) { 
				
				String txtPassword1 = password1.getText().toString().trim();
				String txtPassword2 = password2.getText().toString().trim();
				
					if(!txtPassword1.equalsIgnoreCase(txtPassword2)) {
						Toast.makeText(RegisterActivity.this, "Password Mismatch!", Toast.LENGTH_SHORT).show();
					} else {
						// Sign Up
						TasteryAPIManager.getContext().registerAccount(firstName.getText().toString(), 
								lastName.getText().toString(), 
								email.getText().toString(), 
								mobile.getText().toString(), 
								telephone.getText().toString(), 
								address1.getText().toString(), 
								city.getText().toString(), 
								password1.getText().toString(), 
								new OnRequestComplete() {
									@Override
									public void onRequestComplete(Error error) {
										// TODO Auto-generated method stub
										Log.d("TASTERY", "ON REGISTER COMPLETE!");
										
										runOnUiThread(new Runnable() {
											public void run() {
												Toast.makeText(RegisterActivity.this, "A link has been sent to your e-mail for activation of your account. Please activate first then login after.", 
														Toast.LENGTH_SHORT).show();
											}
										});
										
										finish();
									}
								});
					}
				} 
			}); 
		
		deny.setOnClickListener(new OnClickListener(){ 
			@Override 
			public void onClick(View arg0) { 
				RegisterActivity.this.onBackPressed(); 
				} 
			}); 
		} 
	} 