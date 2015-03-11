package rj.hive.tastery;

import rj.hive.database.models.Account;
import rj.hive.tastery.R.id;
import tastery.datamanager.TasteryObject;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmProfileActivity extends Activity {
	public TextView txtFirstName;
	public TextView txtLastName;
	public TextView txtEmail;
	public TextView txtTelephone;
	public TextView txtMobile;
	public TextView txtAddress;
	public Button btnConfirm;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_profile);
		
		/*int titleId = getResources().getIdentifier("action_bar_title", "id",
				"android");
		TextView yourTextView = (TextView) findViewById(titleId);
		yourTextView.setTextAppearance(this,
				android.R.style.TextAppearance_Large);
		yourTextView.setTypeface(Typeface.createFromAsset(getAssets(),
				"fonts/gearb/GearedSlab-Light.ttf"));
		yourTextView.setTextColor(getResources().getColor(R.color.white));*/
		
		txtFirstName = (TextView)findViewById(id.txtFirstName);
		txtLastName = (TextView)findViewById(id.txtLastName);
		txtEmail = (TextView)findViewById(id.txtEmail);
		txtTelephone = (TextView)findViewById(id.txtTelephone);
		txtMobile = (TextView)findViewById(id.txtMobile);
		txtAddress = (TextView)findViewById(id.txtAddress);
		btnConfirm = (Button)findViewById(id.btnConfirm);
		
		Account account = TasteryObject.getContext().currentAccount;
		txtFirstName.setText(account.firstname);
		txtLastName.setText(account.lastname);
		txtEmail.setText(account.email);
		txtTelephone.setText(account.telephone);
		txtMobile.setText(account.mobile);
		txtAddress.setText(account.address);
		
		btnConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ConfirmProfileActivity.this, TermsAndConditionActivity.class);
				ConfirmProfileActivity.this.startActivity(intent);
			}
		});
		
	}
}