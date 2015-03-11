package rj.hive.tastery;

import info.androidhive.iconchanger.DecoratedIconBitmap;
import info.androidhive.slidingmenu.adapter.NavDrawerListAdapter;
import info.androidhive.slidingmenu.model.NavDrawerItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import rj.hive.database.models.Account;
import rj.hive.reusable.FacebookUtility;
import rj.hive.tastery.fragments.FragmentCart;
import rj.hive.tastery.fragments.FragmentContactUs;
import rj.hive.tastery.fragments.FragmentGift;
import rj.hive.tastery.fragments.FragmentHelpFAQ;
import rj.hive.tastery.fragments.FragmentHow;
import rj.hive.tastery.fragments.FragmentMenu;
import rj.hive.tastery.fragments.FragmentPrivacyPolicy;
import rj.hive.tastery.fragments.FragmentTermsAndCondition;
import rj.hive.tastery.fragments.FragmentWhat;
import rj.hive.tastery.fragments.FragmentWho;
import rj.hive.tastery.fragments.FragmentWhy;
import tastery.datamanager.TasteryAPIManager;
import tastery.datamanager.TasteryAPIManager.OnLoginComplete;
import tastery.datamanager.TasteryObject;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;

public class MainActivity extends FragmentActivity {
	private GraphUser user;

	private static final String TAG = "MainFragment";
	public Fragment fragment = null;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;

	private CharSequence mTitle;

	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	private Menu menu;
	public int itemCount;
	public final String CART = "CART";

	private Typeface font_gearb, font_brandon;

	public void setCartValue(int a) {
		if (a <= 0)
			return;
		Drawable[] layers = new Drawable[2];
		layers[0] = getResources().getDrawable(R.drawable.ic_user);
		layers[1] = new DecoratedIconBitmap(this, R.drawable.ic_user, a).mBitmap;

		LayerDrawable layerDrawable = new LayerDrawable(layers);

		menu.findItem(R.id.action_user).setIcon(layerDrawable);
	}

	private UiLifecycleHelper uiHelper;

	@SuppressWarnings("deprecation")
	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (state.isOpened()) {
			Request getMe = Request.newMeRequest(session,
					new GraphUserCallback() {
						@Override
						public void onCompleted(GraphUser user,
								Response response) {
							Log.d("FACEBOOK", "onCompleted");
							if (user != null) {
								MainActivity.this.user = user;
								updateUI();
							}
						}
					});
			getMe.executeAsync();
		}
		updateUI();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			uiHelper = new UiLifecycleHelper(this, statusCallback);
			uiHelper.onCreate(savedInstanceState);

			font_brandon = Typeface.createFromAsset(getAssets(),
					"fonts/brandon/Brandon_reg.otf");
			font_gearb = Typeface.createFromAsset(getAssets(),
					"fonts/gearb/GearedSlab-Light.ttf");

			final LayoutInflater inflater = getLayoutInflater().cloneInContext(
					this);
			final View view = inflater.inflate(R.layout.activity_main, null);
			setContentView(view);

			mTitle = mDrawerTitle = getTitle();

			int titleId = getResources().getIdentifier("action_bar_title",
					"id", "android");
			TextView yourTextView = (TextView) findViewById(titleId);
			yourTextView.setTextAppearance(this,
					android.R.style.TextAppearance_Large);
			yourTextView.setTypeface(font_gearb);
			yourTextView.setTextColor(getResources().getColor(R.color.white));
			navMenuTitles = getResources().getStringArray(
					R.array.nav_drawer_items);
			navMenuIcons = getResources().obtainTypedArray(
					R.array.nav_drawer_icons);

			mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
			mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

			navDrawerItems = new ArrayList<NavDrawerItem>();

			navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
					.getResourceId(0, -1)));
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
					.getResourceId(1, -1)));
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
					.getResourceId(2, -1)));
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
					.getResourceId(3, -1)));
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
					.getResourceId(4, -1)));
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
					.getResourceId(5, -1)));
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons
					.getResourceId(6, -1)));
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons
					.getResourceId(7, -1)));
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons
					.getResourceId(8, -1)));
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], navMenuIcons
					.getResourceId(9, -1)));

			final String PREF_NAME = "TasterySession";
			final String PREF_SETTINGS = "TasterySettings";
			final String CURRENT_DATE = "DATE";
			int PRIVATE_MODE = 0;

			SharedPreferences prefSettings = this.getApplicationContext()
					.getSharedPreferences(PREF_SETTINGS, PRIVATE_MODE);
			// Toast.makeText(this.getApplicationContext(),
			// prefSettings.getString(CURRENT_DATE, null), Toast.LENGTH_SHORT);

			navMenuIcons.recycle();

			mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

			adapter = new NavDrawerListAdapter(getApplicationContext(),
					navDrawerItems);
			mDrawerList.setAdapter(adapter);

			getActionBar().setDisplayHomeAsUpEnabled(true);
			// getActionBar().setHomeButtonEnabled(true);

			mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
					R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
				public void onDrawerClosed(View view) {
					getActionBar().setTitle(mTitle);
					invalidateOptionsMenu();
				}

				public void onDrawerOpened(View drawerView) {
					getActionBar().setTitle(mDrawerTitle);
					invalidateOptionsMenu();
				}
			};
			mDrawerLayout.setDrawerListener(mDrawerToggle);

			if (savedInstanceState == null) {
				displayView(0);
			} else {
			}

			invalidateOptionsMenu();

			Timer myTimer = new Timer();
			myTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					invalidateOptionsMenu();
					supportInvalidateOptionsMenu();
				}
			}, 0, 1000);

		} catch (NullPointerException e) {
			// Log.e("Error", e.getMessage());
		}
	}

	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		this.menu = menu;
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {

		case R.id.action_cart:
			displayView(10);
			return true;
		case R.id.action_user_data:

			if (TasteryObject.getContext().isLoggedIn()) {
				final Dialog dialog = new Dialog(this, R.style.PauseDialog);
				dialog.setContentView(R.layout.dialog_user);

				Button profile = (Button) dialog.findViewById(R.id.profile);
				Button logout = (Button) dialog.findViewById(R.id.logout);

				profile.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								ProfileActivity.class);
						MainActivity.this.startActivity(intent);
					}

				});

				logout.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {

					}
				});
				dialog.show();
			} else {
				loginPrompt();
			}
			/*
			 * if (enableButtons && user != null) { final Dialog dialog = new
			 * Dialog(this, R.style.PauseDialog);
			 * dialog.setContentView(R.layout.dialog_user);
			 * 
			 * Button profile = (Button)dialog.findViewById(R.id.profile);
			 * Button logout = (Button)dialog.findViewById(R.id.logout);
			 * 
			 * logout.setOnClickListener(new OnClickListener() {
			 * 
			 * @Override public void onClick(View arg0) { callFacebookLogout();
			 * dialog.dismiss(); } });
			 * 
			 * dialog.show(); } else { final Dialog dialog = new Dialog(this,
			 * R.style.PauseDialog);
			 * dialog.setContentView(R.layout.dialog_login);
			 * 
			 * 
			 * final EditText email = (EditText)dialog.findViewById(R.id.edit1);
			 * final EditText password =
			 * (EditText)dialog.findViewById(R.id.edit2);
			 * 
			 * 
			 * Button signup = (Button)dialog.findViewById(R.id.signup); Button
			 * login = (Button)dialog.findViewById(R.id.login);
			 * 
			 * 
			 * 
			 * signup.getBackground().setColorFilter(0xFF76b4f2,
			 * PorterDuff.Mode.MULTIPLY);
			 * 
			 * login.setOnClickListener(new OnClickListener(){
			 * 
			 * @Override public void onClick(View arg0) { ArrayList<Object> objs
			 * = new ArrayList<Object>(); objs.add(MainActivity.this);
			 * objs.add(dialog); JSONFetcher jsonParse = new
			 * JSONFetcher(MainActivity.this,
			 * "http://tastery.ph/index.php?route=account/login&json=",
			 * URLType.LOGIN, objs, true);
			 * jsonParse.setLoadingMessage("Please wait...");
			 * 
			 * BasicNameValuePair[] params = new BasicNameValuePair[2];
			 * 
			 * params[0] = new BasicNameValuePair("email",
			 * email.getText().toString().trim()); params[1] = new
			 * BasicNameValuePair("password",
			 * password.getText().toString().trim()); jsonParse.execute(params);
			 * 
			 * }
			 * 
			 * });
			 * 
			 * signup.setOnClickListener(new OnClickListener(){
			 * 
			 * @Override public void onClick(View v) { Intent intent = new
			 * Intent(MainActivity.this, RegisterActivity.class);
			 * MainActivity.this.startActivity(intent); } });
			 * 
			 * MainActivity.this.user = user; updateUI(); Button button =
			 * (Button)dialog.findViewById(R.id.fbBtn);
			 * button.setOnClickListener(new OnClickListener(){
			 * 
			 * @Override public void onClick(View arg0) { onClickLogin();
			 * dialog.dismiss(); }
			 * 
			 * }); Button twitterButton =
			 * (Button)dialog.findViewById(R.id.twitter);
			 * twitterButton.setOnClickListener(new OnClickListener(){
			 * 
			 * @Override public void onClick(View arg0) { checkTwitter(); }
			 * 
			 * });
			 * 
			 * dialog.show(); }
			 */
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void loginPrompt() {
		final Dialog dialog = new Dialog(this, R.style.PauseDialog);
		dialog.setContentView(R.layout.dialog_login);

		final EditText email = (EditText) dialog.findViewById(R.id.edit1);
		final EditText password = (EditText) dialog.findViewById(R.id.edit2);

		Button signup = (Button) dialog.findViewById(R.id.signup);
		Button login = (Button) dialog.findViewById(R.id.login);
		signup.getBackground().setColorFilter(0xFF76b4f2,
				PorterDuff.Mode.MULTIPLY);

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				TasteryAPIManager.getContext().loginWithUsernameAndPassword(
						email.getText().toString(),
						password.getText().toString(), new OnLoginComplete() {

							@Override
							public void onLoginComplete(Account account) {
								// TODO Auto-generated method stub
								dialog.dismiss();

								if (account != null) {
									TasteryObject.getContext().currentAccount = account;
									runOnUiThread(new Runnable() {
										public void run() {
											Toast.makeText(MainActivity.this,
													"Login Successful!",
													Toast.LENGTH_SHORT).show();
										}
									});

								} else {
									runOnUiThread(new Runnable() {
										public void run() {
											Toast.makeText(MainActivity.this,
													"Login Failed!",
													Toast.LENGTH_SHORT).show();
										}
									});

								}

							}
						});
			}

		});

		signup.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						RegisterActivity.class);
				MainActivity.this.startActivity(intent);
				dialog.dismiss();
			}
		});
		
		MainActivity.this.user = user;
		
		Button button = (Button) dialog.findViewById(R.id.fbBtn);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				Log.d("TASTERY", "FACEBOOK BUTTON");
				callFacebookLogout();
				
			}
		});
		
		Button twitterButton = (Button) dialog.findViewById(R.id.twitter);
		twitterButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			}

		});

		dialog.show();
	}

	public void showLoginFailedPrompt() {
		/*
		 * Handler handler = new Handler(new Callback() {
		 * 
		 * @Override public boolean handleMessage(Message arg0) { // TODO
		 * Auto-generated method stub Toast.makeText(getApplicationContext(),
		 * "Login Failed!", Toast.LENGTH_SHORT).show(); return false; } });
		 * 
		 * handler.dispatchMessage(null);
		 */

		Log.e("TASTERY", "Login Failed");
	}

	
//	 public void checkTwitter() { 
//		 try { 
//			 Twitter twitter = new TwitterFactory().getInstance(); 
//			 RequestToken requestToken = twitter.getOAuthRequestToken();
//	 
//			 Log.e("twitter","Got request token."); Log.e("twitter","Request token: "
//					 + requestToken.getToken()); Log.e("twitter","Request token secret: " +
//							 requestToken.getTokenSecret());
//	 
//					 AccessToken accessToken = null;
//	 
//					 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//					 while (null == accessToken) {
//						 	Log.e("twitter","Open the following URL and grant access to your account:"
//						 			); 
//						 	System.out.println(requestToken.getAuthorizationURL()); 
//					 }
//	/*try {
//	 * Desktop.getDesktop().browse(new URI(requestToken.getAuthorizationURL()));
//	 * } catch (UnsupportedOperationException ignore) { } catch (IOException
//	 * ignore) { } catch (URISyntaxException e) { throw new AssertionError(e); }
//	 *
//	/*
//	 * System.out.print(
//	 * "Enter the PIN(if available) and hit enter after you granted access.[PIN]:"
//	 * ); String pin = br.readLine(); try { if (pin.length() > 0) { accessToken
//	 * = twitter.getOAuthAccessToken(requestToken, pin); } else { accessToken =
//	 * twitter.getOAuthAccessToken(requestToken); } } catch (TwitterException
//	 * te) { if (401 == te.getStatusCode()) {
//	 * Log.e("twitter","Unable to get the access token."); } else {
//	 * te.printStackTrace(); } } } Log.e("twitter","Got access token.");
//	 * Log.e("twitter","Access token: " + accessToken.getToken());
//	 * Log.e("twitter","Access token secret: " + accessToken.getTokenSecret());
//	 */ 
//	 } catch (TwitterException te) {
//		 Log.e("twitter","Failed to get accessToken: " + te.getMessage()); 
//	} catch (IOException ioe) { 
//		ioe.printStackTrace();
//	}
//	 Log.e("twitter","Failed to read the system input."); } }
	 

	public void callFacebookLogout() {
		Session session = Session.getActiveSession();
		if (session != null) {

			if (!session.isClosed()) {
				session.closeAndClearTokenInformation();
			}
		} else {
			session = new Session(this);
			Session.setActiveSession(session);
			session.closeAndClearTokenInformation();
		}
		updateUI();
	}

	private void onClickLogin() {
		Session session = Session.getActiveSession();
		if (!session.isOpened() && !session.isClosed()) {
			session.openForRead(new Session.OpenRequest(this).setPermissions(
					Arrays.asList("public_profile", "email", "user_friends"))
					.setCallback(statusCallback));
		} else {
			Session.openActiveSession(this, true, statusCallback);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(final Menu menu) {
		menu.findItem(R.id.action_cart).setTitle(
				"(" + TasteryObject.getContext().cartList.size() + ") Cart");
		menu.findItem(R.id.action_user_data).setTitle("Account");
		this.setCartValue(TasteryObject.getContext().cartList.size());

		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */

	public void displayView(int position) {
		fragment = null;
		switch (position) {

		case 0:
			fragment = new FragmentMenu();
			break;
		case 1:
			fragment = new FragmentWho();
			break;
		case 2:
			fragment = new FragmentWhat();
			break;
		case 3:
			fragment = new FragmentWhy();
			break;
		case 4:
			fragment = new FragmentHow();
			break;
		case 5:
			fragment = new FragmentGift();
			break;
		case 6:
			fragment = new FragmentContactUs();
			break;
		case 7:
			fragment = new FragmentHelpFAQ();
			break;
		case 8:
			fragment = new FragmentPrivacyPolicy();
			break;
		case 9:
			fragment = new FragmentTermsAndCondition();
			break;
		case 10:
			fragment = new FragmentCart();
			break;
		default:
			break;
		}

		if (fragment != null && !(position > 9)) {
			FragmentManager fragmentManager = this.getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else if (position == 10) {
			FragmentManager fragmentManager = this.getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle("Cart");
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {

			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onBackPressed() {
		final Dialog dialog = new Dialog(this, R.style.PauseDialog);
		dialog.setContentView(R.layout.dialog_quit);

		final Button quit = (Button) dialog.findViewById(R.id.quit);
		final Button cancel = (Button) dialog.findViewById(R.id.cancel);

		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		quit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				MainActivity.super.onBackPressed();
			}
		});
		dialog.show();
	}

	private void updateUI() {

		Session session = Session.getActiveSession();
		boolean enableButtons = (session != null && session.isOpened());

		if (enableButtons && user != null) {
			if (menu != null) {
				menu.findItem(R.id.action_user_data).setTitle(
						user.getFirstName());
				Bitmap a = FacebookUtility.getFacebookProfilePicture(user
						.getId());
				if (a != null)
					menu.findItem(R.id.action_user_data).setIcon(
							new BitmapDrawable(getResources(), a));

			}

		} else {
			if (menu != null) {
				menu.findItem(R.id.action_user_data).setTitle("Log In");
				menu.findItem(R.id.action_user_data).setIcon(
						this.getResources().getDrawable(R.drawable.ic_login));
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		uiHelper.onResume();
		invalidateOptionsMenu();

	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
		@Override
		public void onError(FacebookDialog.PendingCall pendingCall,
				Exception error, Bundle data) {
			// Log.e("HelloFacebook", String.format("Error: %s",
			// error.toString()));
		}

		@Override
		public void onComplete(FacebookDialog.PendingCall pendingCall,
				Bundle data) {
			Log.e("HelloFacebook", "Success!");
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private Session.StatusCallback statusCallback = new SessionStatusCallback();

	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	}

}
