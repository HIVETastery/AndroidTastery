package rj.hive.tastery.fragments;

import info.androidhive.slidingmenu.adapter.DayDrawerListAdapter;
import info.androidhive.slidingmenu.adapter.PlaceDrawerListAdapter;
import info.androidhive.slidingmenu.model.DayDrawerItem;
import info.androidhive.slidingmenu.model.PlaceDrawerItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import rj.hive.database.models.CartMap;
import rj.hive.database.models.Product;
import rj.hive.reusable.ReusableUtility;
import rj.hive.sessionmanager.SessionManager;
import rj.hive.tastery.R;
import tastery.datamanager.TasteryAPIManager;
import tastery.datamanager.TasteryAPIManager.OnAddToCartItemsComplete;
import tastery.datamanager.TasteryAPIManager.OnGetCartItemsComplete;
import tastery.datamanager.TasteryObject;
import tastery.datamanager.TasteryProductsAdapter;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class FragmentMenu extends Fragment {

	View rootView;
	Spinner spinner;
	public ScrollView scrollView1;

	DisplayImageOptions options;

	private ArrayList<DayDrawerItem> daysItem;
	private ArrayList<PlaceDrawerItem> placesItem;
	private DayDrawerListAdapter daysAdapter;
	PlaceDrawerListAdapter placesAdapter;

	private ListView productsList;
	private Button btnOrderSet;
	private List<Product> listProducts;
	private TasteryProductsAdapter listProductsAdapter;
	private Product productSetForTheDay;

	public Dialog menuDialog;

	public FragmentMenu() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public void showFoodDialog(final Product product) {
		menuDialog = new Dialog(FragmentMenu.this.getActivity(),
				R.style.FoodDialog);
		menuDialog.setContentView(R.layout.dialog_food);
		Button btnAddToTummy = (Button) menuDialog
				.findViewById(R.id.addtotummy);
		TextView addAsSetText = (TextView) menuDialog
				.findViewById(R.id.headertext);
		ImageView image = (ImageView) menuDialog.findViewById(R.id.imageView1);
		TextView text = (TextView) menuDialog.findViewById(R.id.texttitle);
		TextView textdesc = (TextView) menuDialog.findViewById(R.id.text2);
		TextView pricetext = (TextView) menuDialog.findViewById(R.id.price);

		if (product.thumb != null) {
			ImageLoader.getInstance().displayImage(product.thumb, image,
					options);
			image.setVisibility(View.VISIBLE);

			RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) text
					.getLayoutParams();
			lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
			text.setLayoutParams(lp);
			text.setText(product.name);

			addAsSetText.setVisibility(View.GONE);
		} else {

			RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) text
					.getLayoutParams();
			lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
			text.setLayoutParams(lp);
			text.setText("Add as a Set: " + product.name);
			image.setVisibility(View.GONE);
			addAsSetText.setVisibility(View.VISIBLE);
		}

		textdesc.setText(ReusableUtility.removePortion(product.description));
		pricetext.setText(product.price);

		btnAddToTummy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

				String currentDate = getCurrentDate();
				TasteryAPIManager.getContext().addToCartItems(product.id, 1,
						"Taguig", currentDate, new OnAddToCartItemsComplete() {

							@Override
							public void onAddToCartItemsComplete(
									final String success, Error error) {
								// TODO Auto-generated method stub

								menuDialog.dismiss();

								if (error == null) {
									getActivity().runOnUiThread(new Runnable() {
										public void run() {
											Toast.makeText(getActivity(),
													Html.fromHtml(success),
													Toast.LENGTH_SHORT).show();

											TasteryAPIManager
													.getContext()
													.getCartItems(
															new OnGetCartItemsComplete() {

																@Override
																public void onGetCartItemsComplete(
																		List<CartMap> cartItems) {
																	// TODO
																	// Auto-generated
																	// method
																	// stub

																	TasteryObject
																			.getContext().cartList = cartItems;
																}
															});

										}
									});
								} else {
									getActivity().runOnUiThread(new Runnable() {
										public void run() {
											Toast.makeText(
													getActivity(),
													"Add Items to Cart Failed!",
													Toast.LENGTH_SHORT).show();
										}
									});
								}
							}
						});
			}
		});

		menuDialog.show();
	}

	public String getCurrentDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM. dd, yyyy");
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}

	public String getString(Calendar cal) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy");
		return dateFormat.format(cal.getTime());
	}

	public String getDayOfWeek(Calendar cal) {
		String weekDay;
		SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
		weekDay = dayFormat.format(cal.getTime());
		return weekDay;
	}

	public String getWholeDateString(Calendar cal) {
		return getDayOfWeek(cal) + ", " + getString(cal);
	}

	public void setDateArray(int interval, ArrayList<DayDrawerItem> daysItem,
			Calendar cal) {
		cal.add(Calendar.DATE, interval);

		for (int i = 0; i < 5; i++) {
			if (interval >= 0) {
				daysItem.add(new DayDrawerItem(getWholeDateString(cal),
						getDayOfWeek(cal), true));
			} else {
				daysItem.add(new DayDrawerItem(getWholeDateString(cal),
						getDayOfWeek(cal), false));
			}
			cal.add(Calendar.DATE, 1);
			++interval;
		}
	}

	public void integrateProducts() {
		spinner = (Spinner) rootView.findViewById(R.id.spinner);
		if (spinner != null) {
			spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parentView,
						View selectedItemView, int position, long id) {
					// Toast.makeText(getActivity(), "Position : " +
					// spinner.getSelectedItemPosition() ,
					// Toast.LENGTH_SHORT).show();
					spinnerOnPress();
				}

				@Override
				public void onNothingSelected(AdapterView<?> parentView) {
					// your code here
				}

			});
			// Toast.makeText(getActivity(), "Position : " +
			// spinner.getSelectedItemPosition() , Toast.LENGTH_SHORT).show();
			spinnerOnPress();
		} else {
			Toast.makeText(getActivity(), "Error has occured.",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void spinnerOnPress() {
		// Toast.makeText(getActivity(), "Position : " +
		// spinner.getSelectedItemPosition() , Toast.LENGTH_SHORT).show();
		final Spinner spinnerplace = (Spinner) rootView
				.findViewById(R.id.spinnerplaces);
		PlaceDrawerItem placeitem = (PlaceDrawerItem) spinnerplace
				.getSelectedItem();
		if (!placeitem.getId().equals("68")) {
			return;
		}
	}

	// final ScrollView scrollView1 =
	// (ScrollView)rootView.findViewById(R.id.scrollView1);

	/*
	 * final TextView cannotconnect =
	 * (TextView)rootView.findViewById(R.id.cannotconnect); final TextView
	 * no_menu = (TextView)rootView.findViewById(R.id.errormessage); final
	 * Button retry = (Button)rootView.findViewById(R.id.retry);
	 * 
	 * DayDrawerItem item = (DayDrawerItem)spinner.getSelectedItem();
	 * 
	 * DatabaseHelper dbHelper = new
	 * DatabaseHelper(FragmentMenu.this.getActivity()); ArrayList<Product>
	 * products = dbHelper.getProductsPerDay(item.getSubtitle());
	 * ArrayList<Product> products1 =
	 * dbHelper.getProductsPerDay(item.getSubtitle() + "SetMeal");
	 * 
	 * if(products1.size() > 0) { final Product product = products1.get(0);
	 * 
	 * final Button addSet = (Button)rootView.findViewById(R.id.addasset);
	 * addSet.setOnClickListener(new OnClickListener(){
	 * 
	 * @Override public void onClick(View arg0) { /* If the time comes that the
	 * set has thumbnails already, please replace the fucking null between the
	 * price argument and description to product.thumb, asshole
	 */
	/*
	 * showFoodDialog(product.id, product.name, product.price, null,
	 * product.description); }
	 * 
	 * }); }
	 * 
	 * if(!ReusableUtility.isDevelopersMode) if(!item.isEnabled) {//No menu
	 * no_menu.setVisibility(View.VISIBLE);
	 * scrollView1.setVisibility(View.GONE);
	 * no_menu.setText("The menu for this day will be updated on Sunday!");
	 * 
	 * cannotconnect.setVisibility(View.GONE); retry.setVisibility(View.GONE);
	 * return; }
	 * 
	 * for(int i = 0; i < products.size(); i++) { final Product prod =
	 * products.get(i); final ViewHolder holder = new ViewHolder(); switch(i) {
	 * case 0: holder.textView = (TextView)rootView.findViewById(R.id.text1);
	 * holder.imageView = (ImageView) rootView.findViewById(R.id.image1); //
	 * holder.progressBar = (ProgressBar) rootView.findViewById(R.id.progress1);
	 * break; case 1: holder.textView =
	 * (TextView)rootView.findViewById(R.id.text2); holder.imageView =
	 * (ImageView) rootView.findViewById(R.id.image2); // holder.progressBar =
	 * (ProgressBar) rootView.findViewById(R.id.progress2); break; case 2:
	 * holder.textView = (TextView)rootView.findViewById(R.id.text3);
	 * holder.imageView = (ImageView) rootView.findViewById(R.id.image3); //
	 * holder.progressBar = (ProgressBar) rootView.findViewById(R.id.progress3);
	 * break; } holder.textView.setText(prod.name);
	 * holder.imageView.setClickable(true);
	 * holder.imageView.setOnClickListener(new OnClickListener(){
	 * 
	 * @Override public void onClick(View arg0) { showFoodDialog(prod.id,
	 * prod.name, prod.price, prod.thumb, prod.description); } });
	 * ImageLoader.getInstance() .displayImage(prod.thumb, holder.imageView,
	 * options, new SimpleImageLoadingListener() {
	 * 
	 * @Override public void onLoadingStarted(String imageUri, View view) { //
	 * holder.progressBar.setProgress(0); //
	 * holder.progressBar.setVisibility(View.VISIBLE); }
	 * 
	 * @Override public void onLoadingFailed(String imageUri, View view,
	 * FailReason failReason) { // holder.progressBar.setVisibility(View.GONE);
	 * }
	 * 
	 * @Override public void onLoadingComplete(String imageUri, View view,
	 * Bitmap loadedImage) { // holder.progressBar.setVisibility(View.GONE); }
	 * }, new ImageLoadingProgressListener() {
	 * 
	 * @Override public void onProgressUpdate(String imageUri, View view, int
	 * current, int total) { // holder.progressBar.setProgress(Math.round(100.0f
	 * * current / total)); } });
	 * 
	 * } /* ViewHolder holder1 = new ViewHolder(), holder2 = new ViewHolder(),
	 * holder3 = new ViewHolder(); holder1.textView =
	 * (TextView)rootView.findViewById(R.id.text1); holder1.imageView =
	 * (ImageView) rootView.findViewById(R.id.image1); // holder1.progressBar =
	 * (ProgressBar) rootView.findViewById(R.id.progress1);
	 * 
	 * holder2.textView = (TextView)rootView.findViewById(R.id.text2);
	 * holder2.imageView = (ImageView) rootView.findViewById(R.id.image2); //
	 * holder2.progressBar = (ProgressBar)
	 * rootView.findViewById(R.id.progress2);
	 * 
	 * holder3.textView = (TextView)rootView.findViewById(R.id.text3);
	 * holder3.imageView = (ImageView) rootView.findViewById(R.id.image3); //
	 * holder3.progressBar = (ProgressBar)
	 * rootView.findViewById(R.id.progress3);
	 * 
	 * 
	 * if(products.size() == 0) {//No menu no_menu.setVisibility(View.VISIBLE);
	 * scrollView1.setVisibility(View.GONE);
	 * no_menu.setText("No Menu for this day sorry =("); /* String
	 * productsString = ""; List<Product> productsList =
	 * TasteryObject.getContext().productsList; for(Product productItem :
	 * productsList) { productsString = productsString + " " + productItem.name;
	 * } no_menu.setText(productsString);
	 * 
	 * cannotconnect.setVisibility(View.GONE); retry.setVisibility(View.GONE); }
	 * else if(products.size() == 1) { holder1.show(); holder2.hide();
	 * holder3.hide(); } else if(products.size() == 2) { holder1.show();
	 * holder2.show(); holder3.hide(); } else { holder1.show(); holder2.show();
	 * holder3.show();
	 * 
	 * no_menu.setVisibility(View.GONE);
	 * scrollView1.setVisibility(View.VISIBLE);
	 * cannotconnect.setVisibility(View.GONE); retry.setVisibility(View.GONE);
	 * no_menu.setVisibility(View.GONE); }
	 * 
	 * Toast.makeText(FragmentMenu.this.getActivity(), item.getTitle(),
	 * Toast.LENGTH_SHORT).show();
	 * 
	 * }
	 */

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try {
			SessionManager session = new SessionManager(getActivity()
					.getApplicationContext());
			rootView = inflater.inflate(R.layout.fragment_menu_main, container,
					false);
			initializeWidgets();

			final Spinner spinnerplaces = (Spinner) rootView
					.findViewById(R.id.spinnerplaces);
			placesItem = new ArrayList<PlaceDrawerItem>();
			placesItem.add(new PlaceDrawerItem("68", "Taguig",
					"Taguig (BGC and McKinley Hill)", true));
			placesItem
					.add(new PlaceDrawerItem("69", "Alabang", "Alabang", true));
			placesItem.add(new PlaceDrawerItem("76", "Makati", "Makati", true));
			placesItem.add(new PlaceDrawerItem("77", "Ortigas/Libis",
					"Ortigas/Libis", true));
			placesAdapter = new PlaceDrawerListAdapter(getActivity()
					.getApplicationContext(), placesItem);
			spinnerplaces.setAdapter(placesAdapter);
			spinnerplaces
					.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parentView,
								View selectedItemView, int position, long id) {

							if (spinnerplaces != null) {
								PlaceDrawerItem item = (PlaceDrawerItem) spinnerplaces
										.getSelectedItem();

								if (item.getId().equals("68")) {// TAGUIG
									spinnerOnPress();
								} else {
									scrollView1 = (ScrollView) rootView
											.findViewById(R.id.scrollView1);
									scrollView1.setVisibility(View.GONE);

								}
							}

						}

						@Override
						public void onNothingSelected(AdapterView<?> parentView) {
							// your code here
						}

					});

			if (session.getCurrentDateFormat() != null) {
				Date convertedDate = session.getCurrentDateFormat();
				scrollView1 = (ScrollView) rootView
						.findViewById(R.id.scrollView1);

				daysItem = new ArrayList<DayDrawerItem>();
				Calendar cal = Calendar.getInstance();
				cal.setTime(convertedDate);

				int day = cal.get(Calendar.DAY_OF_WEEK);
				switch (day) {
				case Calendar.MONDAY:
					setDateArray(0, daysItem, cal);
					break;
				case Calendar.TUESDAY:
					setDateArray(-1, daysItem, cal);
					break;
				case Calendar.WEDNESDAY:
					setDateArray(-2, daysItem, cal);
					break;
				case Calendar.THURSDAY:
					setDateArray(-3, daysItem, cal);
					break;
				case Calendar.FRIDAY:
					setDateArray(-4, daysItem, cal);
					break;
				case Calendar.SATURDAY:
					setDateArray(-5, daysItem, cal);
					break;
				case Calendar.SUNDAY:
					setDateArray(1, daysItem, cal);
					break;
				}

				daysAdapter = new DayDrawerListAdapter(getActivity()
						.getApplicationContext(), daysItem);
				spinner.setAdapter(daysAdapter);

				day = day + 1;
				if (day == 7) {
					day = 0;
				}

				switch (day) {
				case Calendar.MONDAY:
					spinner.setSelection(0);
					break;
				case Calendar.TUESDAY:
					spinner.setSelection(1);
					break;
				case Calendar.WEDNESDAY:
					spinner.setSelection(2);
					break;
				case Calendar.THURSDAY:
					spinner.setSelection(3);
					break;
				case Calendar.FRIDAY:
					spinner.setSelection(4);
					break;
				case Calendar.SATURDAY:
					spinner.setSelection(4);
					break;
				case Calendar.SUNDAY:
					spinner.setSelection(0);
					break;
				}

				scrollView1.setVisibility(View.GONE);
			}

			return rootView;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return rootView;
		}
	}

	/*
	 * class ViewHolder { ImageView imageView; // ProgressBar progressBar;
	 * TextView textView; public void hide() {
	 * imageView.setVisibility(View.GONE); textView.setVisibility(View.GONE); }
	 * public void show() { imageView.setVisibility(View.VISIBLE);
	 * textView.setVisibility(View.VISIBLE); }
	 */

	private void initializeWidgets() {
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this
				.getActivity()));
		productsList = (ListView) rootView.findViewById(R.id.productsList);

		Calendar calendar = Calendar.getInstance();

		int today = calendar.get(Calendar.DAY_OF_WEEK);
		today = today + 1;

		if (today == 7) {
			today = 0;
		}

		String currentDay = "";
		productsList.setAlpha(1.0F);
		switch (today) {
		case Calendar.SUNDAY:
			currentDay = "Monday";
			break;
		case Calendar.MONDAY:
			currentDay = "Tuesday";
			break;
		case Calendar.TUESDAY:
			currentDay = "Wednesday";
			break;
		case Calendar.WEDNESDAY:
			currentDay = "Thursday";
			break;
		case Calendar.THURSDAY:
			currentDay = "Friday";
			break;
		case Calendar.FRIDAY:
			currentDay = "Friday";
			// productsList.setAlpha(0.3F);
			break;
		case Calendar.SATURDAY:
			currentDay = "Friday";
			productsList.setAlpha(0.3F);
			break;
		}

		updateMenuForDay(currentDay);
		productSetForTheDay = TasteryObject.getContext().getProductSetForDay(
				currentDay);

		productsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (productsList.getAlpha() > 0.3F) {
					Product selectedProduct = listProducts.get(position);
					showFoodDialog(selectedProduct);
				}

			}

		});

		btnOrderSet = (Button) rootView.findViewById(R.id.btnOrderSet);
		btnOrderSet.setText("Order Set");
		btnOrderSet.setTextColor(Color.WHITE);
		btnOrderSet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub

				String currentDate = getCurrentDate();
				TasteryAPIManager.getContext().addToCartItems(
						productSetForTheDay.id, 1, "Taguig", currentDate,
						new OnAddToCartItemsComplete() {

							@Override
							public void onAddToCartItemsComplete(
									final String success, Error error) {
								// TODO Auto-generated method stub

								if (error == null) {
									getActivity().runOnUiThread(new Runnable() {
										public void run() {
											Toast.makeText(getActivity(),
													Html.fromHtml(success),
													Toast.LENGTH_SHORT).show();

											TasteryAPIManager
													.getContext()
													.getCartItems(
															new OnGetCartItemsComplete() {

																@Override
																public void onGetCartItemsComplete(
																		List<CartMap> cartItems) {
																	// TODO
																	// Auto-generated
																	// method
																	// stub

																	TasteryObject
																			.getContext().cartList = cartItems;
																}
															});

										}
									});
								} else {
									getActivity().runOnUiThread(new Runnable() {
										public void run() {
											Toast.makeText(
													getActivity(),
													"Add Items to Cart Failed!",
													Toast.LENGTH_SHORT).show();
										}
									});
								}
							}
						});

			}
		});

		spinner = (Spinner) rootView.findViewById(R.id.spinner);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view,
					int i, long l) {
				final List<String> days = new ArrayList<String>();
				days.add("Monday");
				days.add("Tuesday");
				days.add("Wednesday");
				days.add("Thursday");
				days.add("Friday");
				days.add("");

				String selectedDay = days.get(i);
				updateMenuForDay(selectedDay);
				;
			}

			public void onNothingSelected(AdapterView<?> adapterView) {
				return;
			}
		});

		TasteryAPIManager.getContext().getCartItems(
				new OnGetCartItemsComplete() {

					@Override
					public void onGetCartItemsComplete(List<CartMap> cartItems) {
						// TODO Auto-generated method stub
						TasteryObject.getContext().cartList = cartItems;
					}
				});
	}

	private void updateMenuForDay(String day) {
		listProducts = TasteryObject.getContext().getProductsForDay(day);
		listProductsAdapter = new TasteryProductsAdapter(getActivity()
				.getApplicationContext(), R.layout.menu_product_item,
				listProducts);
		productsList.setAdapter(listProductsAdapter);
		listProductsAdapter.notifyDataSetChanged();
	}

}