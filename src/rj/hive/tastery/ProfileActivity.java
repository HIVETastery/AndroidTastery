package rj.hive.tastery;

import rj.hive.database.models.Account;
import rj.hive.sessionmanager.SessionManager;
import tastery.datamanager.TasteryObject;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ProfileActivity extends Activity {
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
	public LinearLayout passwordPanel;
	public Button save1, save2, save3, savepass, deny;

	public final void focusOnView(final View v) {
		new Handler().post(new Runnable() {
			@Override
			public void run() {
				int vTop = v.getTop();
				int vBottom = v.getBottom();
				int height = v.getHeight();
				if (scrollView != null)
					scrollView.smoothScrollTo(0, ((vTop + vBottom) / 2)
							- (height));
			}
		});
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_profile_form);
		
		int titleId = getResources().getIdentifier("action_bar_title", "id",
				"android");
		TextView yourTextView = (TextView) findViewById(titleId);
		yourTextView.setTextAppearance(this,
				android.R.style.TextAppearance_Large);
		yourTextView.setTypeface(Typeface.createFromAsset(getAssets(),
				"fonts/gearb/GearedSlab-Light.ttf"));
		yourTextView.setTextColor(getResources().getColor(R.color.white));
		
		scrollView = (ScrollView) findViewById(R.id.scrollView);
		firstName = (EditText) findViewById(R.id.editText1);
		lastName = (EditText) findViewById(R.id.editText2);
		email = (EditText) findViewById(R.id.editText3);
		telephone = (EditText) findViewById(R.id.editText4);
		mobile = (EditText) findViewById(R.id.editText5);
		company = (EditText) findViewById(R.id.company);
		address1 = (EditText) findViewById(R.id.editText6);
		address2 = (EditText) findViewById(R.id.editText7);
		city = (EditText) findViewById(R.id.editText8);
		passwordPanel = (LinearLayout) findViewById(R.id.linearlayout2);
		password1 = (EditText) findViewById(R.id.newPass);
		password2 = (EditText) findViewById(R.id.newPassConfirm);
		save1 = (Button) findViewById(R.id.set1);
		save2 = (Button) findViewById(R.id.set2);
		save3 = (Button) findViewById(R.id.set3);
		savepass = (Button) findViewById(R.id.savepass);
		deny = (Button) findViewById(R.id.deny);
		passwordPanel.setVisibility(View.GONE);
		
		Account account = TasteryObject.getContext().currentAccount;
		
		firstName.setText(account.firstname);
		lastName.setText(account.lastname);
		email.setText(account.email);
		telephone.setText(account.telephone);
		mobile.setText(account.mobile);
		
		firstName.setFocusable(false);
		lastName.setFocusable(false);
		email.setFocusable(false);
		telephone.setFocusable(false);
		mobile.setFocusable(false);
		
		company.setText("");
		address1.setText(account.address);
		// address2.setText(user.get(SessionManager.ADDRESS_2));
		// city.setText();
		
		company.setFocusable(false);
		address1.setFocusable(false);
		address2.setFocusable(false);
		city.setFocusable(false);
		
		save1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (save1.getText().equals("Save")) {
					return;
				}
				firstName.setFocusableInTouchMode(true);
				lastName.setFocusableInTouchMode(true);
				email.setFocusableInTouchMode(true);
				telephone.setFocusableInTouchMode(true);
				mobile.setFocusableInTouchMode(true);
				if (firstName.requestFocus()) {
					getWindow()
							.setSoftInputMode(
									WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				}
				save1.setText("Save");
			}
		});
		save2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (save2.getText().equals("Save")) {
					return;
				}
				company.setFocusableInTouchMode(true);
				address1.setFocusableInTouchMode(true);
				address2.setFocusableInTouchMode(true);
				city.setFocusableInTouchMode(true);
				if (company.requestFocus()) {
					getWindow()
							.setSoftInputMode(
									WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				}
				save2.setText("Save");
			}
		});
		save3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AnimationSet set = new AnimationSet(true);
				Animation animation = new AlphaAnimation(0.0f, 1.0f);
				animation.setDuration(800);
				set.addAnimation(animation);
				animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
						0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
						Animation.RELATIVE_TO_SELF, -1.0f,
						Animation.RELATIVE_TO_SELF, 0.0f);
				animation.setDuration(250);
				set.addAnimation(animation);
				passwordPanel.startAnimation(set);
				passwordPanel.setVisibility(View.VISIBLE);
				scrollView.post(new Runnable() {
					@Override
					public void run() {
						scrollView.fullScroll(View.FOCUS_DOWN);
					}
				});
				save3.setVisibility(View.GONE);
			}
		});
		savepass = (Button) findViewById(R.id.savepass);
		deny = (Button) findViewById(R.id.deny);
		savepass.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			}
		});
		deny.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ProfileActivity.this.onBackPressed();
			}
		});
	}
}