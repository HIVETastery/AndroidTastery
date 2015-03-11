package rj.hive.tastery.fragments;

import info.androidhive.slidingmenu.adapter.QuantityDrawerListAdapter;
import info.androidhive.slidingmenu.adapter.TimeDrawerListAdapter;
import info.androidhive.slidingmenu.model.QuantityDrawerItem;
import info.androidhive.slidingmenu.model.TimeDrawerItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import rj.hive.database.DatabaseHelper;
import rj.hive.database.models.CartMap;
import rj.hive.database.models.Customer;
import rj.hive.database.models.sorter.MyComparator;
import rj.hive.sessionmanager.SessionManager;
import rj.hive.tastery.ConfirmProfileActivity;
import rj.hive.tastery.MainActivity;
import rj.hive.tastery.ProfileActivity;
import rj.hive.tastery.R;
import rj.hive.tastery.R.id;
import rj.hive.tastery.ui.MySpinnerBrandon;
import rj.hive.tastery.ui.MyTextViewBrandon;
import tastery.datamanager.TasteryCartAdapter;
import tastery.datamanager.TasteryObject;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

public class FragmentCart extends Fragment {

	View rootView;
	List<CartMap> cartlist;
	public ListView listCart;
	public Button btnCheckout;
	public TasteryCartAdapter cartAdapter;
	public Dialog cartDialog;
	public CartMap cartItem;
	
	public FragmentCart() {
	}

	ArrayList<CartMap> cartmaps;
	Customer customer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		TasteryAPIManager.getContext().getCartItems(
				new OnGetCartItemsComplete() {

					@Override
					public void onGetCartItemsComplete(List<CartMap> cartItems) {
						// TODO Auto-generated method stub
						TasteryObject.getContext().cartList = cartItems;
						cartlist = cartItems;
						updateCartList();
					}
				});
		*/
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_cart, container, false);
		listCart = (ListView)rootView.findViewById(id.listCart);
		btnCheckout = (Button)rootView.findViewById(id.btnCheckout);
		
		btnCheckout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(TasteryObject.getContext().isLoggedIn()==true) {
					Intent intent = new Intent(getActivity(), ConfirmProfileActivity.class);
					getActivity().startActivity(intent);
				} else {
					Toast.makeText(getActivity(), "Please login first.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		updateCartList();
		
		final LinearLayout table = (LinearLayout) rootView
				.findViewById(R.id.table);
		
		// 23133

		LinearLayout headerRow = new LinearLayout(getActivity());
		headerRow.setOrientation(LinearLayout.HORIZONTAL);
		LayoutParams headerRowParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		headerRow.setLayoutParams(headerRowParams);

		MyTextViewBrandon orderDate = new MyTextViewBrandon(getActivity());
		LayoutParams orderDateParams = new LayoutParams(0,
				LayoutParams.MATCH_PARENT, 0.16f);
		orderDateParams.setMargins(1, 0, 1, 0);

		orderDate.setGravity(Gravity.CENTER);
		orderDate.setTextAppearance(getActivity(),
				android.R.style.TextAppearance_Small);
		orderDate.setTextColor(Color.BLACK);
		orderDate.setLayoutParams(orderDateParams);
		orderDate.setText("ORDER DATE");

		MyTextViewBrandon items = new MyTextViewBrandon(getActivity());
		LayoutParams itemsParams = new LayoutParams(0,
				LayoutParams.MATCH_PARENT, 0.25f);
		itemsParams.setMargins(0, 0, 1, 0);

		items.setGravity(Gravity.CENTER);
		items.setTextAppearance(getActivity(),
				android.R.style.TextAppearance_Small);
		items.setTextColor(Color.BLACK);
		items.setLayoutParams(itemsParams);
		items.setText("ITEMS");

		MyTextViewBrandon qty = new MyTextViewBrandon(getActivity());
		LayoutParams qtyParams = new LayoutParams(0, LayoutParams.MATCH_PARENT,
				0.08f);
		qtyParams.setMargins(0, 0, 1, 0);

		qty.setGravity(Gravity.CENTER);
		qty.setTextAppearance(getActivity(),
				android.R.style.TextAppearance_Small);
		qty.setTextColor(Color.BLACK);
		qty.setLayoutParams(qtyParams);
		qty.setText("QTY");

		/*MyTextViewBrandon deliverDate = new MyTextViewBrandon(getActivity());
		LayoutParams deliverDateParams = new LayoutParams(0,
				LayoutParams.MATCH_PARENT, 0.18f);
		deliverDateParams.setMargins(0, 0, 1, 0);

		deliverDate.setGravity(Gravity.CENTER);
		deliverDate.setTextAppearance(getActivity(),
				android.R.style.TextAppearance_Small);
		deliverDate.setTextColor(Color.BLACK);
		deliverDate.setLayoutParams(deliverDateParams);
		deliverDate.setText("DELIVER DATE");*/

		MyTextViewBrandon time = new MyTextViewBrandon(getActivity());
		LayoutParams timeParams = new LayoutParams(0,
				LayoutParams.MATCH_PARENT, 0.25f);
		timeParams.setMargins(0, 0, 1, 0);

		time.setGravity(Gravity.CENTER);
		time.setTextAppearance(getActivity(),
				android.R.style.TextAppearance_Small);
		time.setTextColor(Color.BLACK);
		time.setLayoutParams(timeParams);
		time.setText("TIME");

		MyTextViewBrandon price = new MyTextViewBrandon(getActivity());
		LayoutParams priceParams = new LayoutParams(0,
				LayoutParams.MATCH_PARENT, 0.25f);
		priceParams.setMargins(0, 0, 1, 0);

		price.setGravity(Gravity.CENTER);
		price.setTextAppearance(getActivity(),
				android.R.style.TextAppearance_Small);
		price.setTextColor(Color.BLACK);
		price.setLayoutParams(priceParams);
		price.setText("PRICE");

		headerRow.addView(orderDate);
		headerRow.addView(items);
		headerRow.addView(qty);
		// headerRow.addView(deliverDate);
		headerRow.addView(time);
		headerRow.addView(price);

		table.addView(headerRow);

		// table.removeAllViews();

		final SessionManager sessionMgr = new SessionManager(this.getActivity()
				.getApplicationContext());

		cartlist = sessionMgr.getCartArray();

		/*if (cartmaps != null) {
			cartmaps = cartlist;
		}*/

		DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
		Date date = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy");
		String currentDate = sdf.format(date);

		if (cartlist != null) {

			for (int i = 0; i < cartlist.size(); i++) {
				CartMap cartitemfirst = cartlist.get(i);
				cartitemfirst.product = dbHelper.getProduct(Integer
						.toString(cartitemfirst.id));
			}

			Collections.sort(cartlist, new MyComparator());

			for (final CartMap cart : cartlist) {

				final LinearLayout row = new LinearLayout(getActivity());
				row.setOrientation(LinearLayout.HORIZONTAL);
				LayoutParams rowParams = new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				rowParams.setMargins(0, 3, 0, 10);
				row.setLayoutParams(rowParams);

				MyTextViewBrandon rowOrderDate = new MyTextViewBrandon(
						getActivity());
				LayoutParams rowOrderDateParams = new LayoutParams(0,
						LayoutParams.WRAP_CONTENT, 0.18f);

				rowOrderDateParams.setMargins(1, 0, 1, 0);

				rowOrderDate.setGravity(Gravity.CENTER);
				rowOrderDate.setTextAppearance(getActivity(),
						android.R.style.TextAppearance_Small);
				rowOrderDate.setTextColor(Color.BLACK);
				rowOrderDate.setLayoutParams(rowOrderDateParams);
				rowOrderDate.setText(cart.getOrderDate());
				rowOrderDate.setPadding(0, 10, 0, 10);

				MyTextViewBrandon rowItems = new MyTextViewBrandon(
						getActivity());
				LayoutParams rowItemsParams = new LayoutParams(0,
						LayoutParams.WRAP_CONTENT, 0.20f);

				rowItemsParams.setMargins(0, 0, 1, 0);

				rowItems.setGravity(Gravity.CENTER);
				rowItems.setTextAppearance(getActivity(),
						android.R.style.TextAppearance_Small);
				rowItems.setTextColor(Color.BLACK);
				rowItems.setLayoutParams(rowItemsParams);
				rowItems.setText(cart.product.name);
				rowItems.setPadding(0, 10, 0, 10);

				Spinner rowQty = getNewQuantitySpinner(cart,
						Integer.toString(cart.id), sessionMgr);
				LayoutParams rowQtyParams = new LayoutParams(0,
						LayoutParams.WRAP_CONTENT, 0.14f);

				rowQtyParams.setMargins(0, 0, 1, 0);

				rowQty.setGravity(Gravity.CENTER | Gravity.TOP);
				rowQty.setLayoutParams(rowQtyParams);

				final MyTextViewBrandon rowDeliverDate = new MyTextViewBrandon(
						getActivity());
				LayoutParams rowDeliverDateParams = new LayoutParams(0,
						LayoutParams.WRAP_CONTENT, 0.18f);

				rowDeliverDateParams.setMargins(0, 0, 1, 0);

				rowDeliverDate.setGravity(Gravity.CENTER);
				rowDeliverDate.setTextAppearance(getActivity(),
						android.R.style.TextAppearance_Small);
				rowDeliverDate.setTextColor(Color.BLACK);
				rowDeliverDate.setLayoutParams(rowDeliverDateParams);
				rowDeliverDate.setText(currentDate);

				cart.setTempDeliverDate(currentDate);

				rowDeliverDate.setPadding(0, 10, 0, 10);
				rowDeliverDate.setClickable(true);
				rowDeliverDate.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						final Dialog dialog = new Dialog(getActivity(),
								R.style.PauseDialog);
						dialog.setContentView(R.layout.dialog_datepicker);
						final DatePicker datepicker = (DatePicker) dialog
								.findViewById(R.id.datepicker);
						Button sync = (Button) dialog.findViewById(R.id.allow);
						Button cancel = (Button) dialog.findViewById(R.id.deny);

						try {
							Calendar cal = Calendar.getInstance();
							final SimpleDateFormat sdf = new SimpleDateFormat(
									"MM.dd.yyyy");
							cal.setTime(sdf.parse(rowDeliverDate.getText()
									.toString()));
							datepicker.updateDate(cal.get(Calendar.YEAR),
									cal.get(Calendar.MONTH),
									cal.get(Calendar.DAY_OF_MONTH));

							sync.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									int day = datepicker.getDayOfMonth();
									int month = datepicker.getMonth();
									int year = datepicker.getYear();

									Calendar calendar = Calendar.getInstance();
									calendar.set(year, month, day);
									rowDeliverDate.setText((sdf.format(calendar
											.getTime())).toString());
									cart.setTempDeliverDate((sdf
											.format(calendar.getTime()))
											.toString());

									dialog.dismiss();
								}
							});

							cancel.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View arg0) {
									dialog.dismiss();
								}
							});

						} catch (ParseException e) {
							e.printStackTrace();
						}
						dialog.show();
					}
				});

				Spinner rowTime = getNewTimeSpinner(cart);
				LayoutParams rowTimeParams = new LayoutParams(0,
						LayoutParams.WRAP_CONTENT, 0.22f);
				rowTimeParams.setMargins(0, 0, 1, 0);

				rowTime.setGravity(Gravity.CENTER | Gravity.TOP);
				rowTime.setLayoutParams(rowTimeParams);

				MyTextViewBrandon rowPrice = new MyTextViewBrandon(
						getActivity());
				LayoutParams rowPriceParams = new LayoutParams(0,
						LayoutParams.WRAP_CONTENT, 0.17f);

				rowPriceParams.setMargins(0, 0, 8, 0);

				Drawable img = getActivity().getApplicationContext()
						.getResources().getDrawable(R.drawable.ic_wrong);

				rowPrice.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
				rowPrice.setTextAppearance(getActivity(),
						android.R.style.TextAppearance_Small);
				rowPrice.setTextColor(Color.BLACK);
				rowPrice.setLayoutParams(rowPriceParams);
				rowPrice.setText(cart.product.price);
				rowPrice.setCompoundDrawablesWithIntrinsicBounds(null, null,
						img, null);
				rowPrice.setCompoundDrawablePadding(3);
				rowPrice.setPadding(0, 10, 0, 10);

				rowPrice.setClickable(true);

				rowPrice.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						final Dialog dialog = new Dialog(getActivity(),
								R.style.PauseDialog);
						dialog.setContentView(R.layout.dialog_removeitem);
						TextView text = (TextView) dialog
								.findViewById(R.id.textv);
						Button yes = (Button) dialog.findViewById(R.id.yes);
						Button no = (Button) dialog.findViewById(R.id.no);

						text.setText("Remove " + cart.product.name
								+ " from cart?");

						yes.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								sessionMgr.removeFromCart(Integer
										.toString(cart.id));
								table.removeView(row);
								dialog.dismiss();
								cartlist.remove(cart);
								getActivity().invalidateOptionsMenu();
								updatePrices();
							}

						});

						no.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								dialog.dismiss();
							}

						});
						dialog.show();
					}

				});

				row.addView(rowOrderDate);
				row.addView(rowItems);
				row.addView(rowQty);
				row.addView(rowDeliverDate);
				row.addView(rowTime);
				row.addView(rowPrice);

				table.addView(row);
			}
		}
		updatePrices();

		final LinearLayout panel2 = (LinearLayout) rootView
				.findViewById(R.id.linearlayout2);

		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				((EditText) arg0).setFocusableInTouchMode(true);
				arg0.setOnClickListener(null);
			}

		};

		return rootView;
	}

	public final void focusOnView(final View v) {
		if (rootView != null) {
			final ScrollView scrollView = (ScrollView) rootView
					.findViewById(R.id.scroll1);
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

	}

	public void updatePrices() {

		SessionManager sessionMgr = new SessionManager(getActivity());
		DatabaseHelper dbHelper = new DatabaseHelper(this.getActivity());
		TextView vattext = (TextView) rootView.findViewById(R.id.textView1);
		TextView totalText = (TextView) rootView.findViewById(R.id.textView2);

		/*
		 * TextView cartempty = (TextView)
		 * rootView.findViewById(R.id.cartempty); if(sessionMgr.getCartAmount()
		 * == 0) { cartempty.setVisibility(View.VISIBLE); } else {
		 * cartempty.setVisibility(View.GONE); }
		 */

		float total = sessionMgr.getTotalAmount(dbHelper);
		float vat = sessionMgr.getVATAmount(dbHelper);

		// vattext.setText("VAT: ₱" + String.format("%.2f", vat));
		// totalText.setText("Total: ₱" + String.format("%.2f", total));
	}

	public Spinner getNewTimeSpinner(final CartMap cartmap) {
		final Spinner spinner = new MySpinnerBrandon(this.getActivity());
		ArrayList<TimeDrawerItem> timeItems = new ArrayList<TimeDrawerItem>();

		timeItems.add(new TimeDrawerItem("8-10am", true));
		timeItems.add(new TimeDrawerItem("10-12pm", true));
		timeItems.add(new TimeDrawerItem("12-2pm", true));
		timeItems.add(new TimeDrawerItem("2-4pm", true));
		timeItems.add(new TimeDrawerItem("4-6pm", true));
		timeItems.add(new TimeDrawerItem("6-8pm", true));

		TimeDrawerListAdapter timeAdapter = new TimeDrawerListAdapter(
				getActivity().getApplicationContext(), timeItems);

		spinner.setAdapter(timeAdapter);

		TimeDrawerItem text = (TimeDrawerItem) spinner.getSelectedItem();

		cartmap.setTempDeliverTime(text.getTitle());

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {

				TimeDrawerItem text = (TimeDrawerItem) spinner
						.getSelectedItem();
				cartmap.setTempDeliverTime(text.getTitle());

			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}

		});

		return spinner;
	}

	public Spinner getNewQuantitySpinner(final CartMap cartmap,
			final String item_id, final SessionManager sessionManager) {

		final Spinner spinner = new MySpinnerBrandon(this.getActivity());
		ArrayList<QuantityDrawerItem> qtyItem = new ArrayList<QuantityDrawerItem>();

		for (int i = 1; i <= 30; i++) {
			qtyItem.add(new QuantityDrawerItem(Integer.toString(i), true));
		}
		QuantityDrawerListAdapter qtyAdapter = new QuantityDrawerListAdapter(
				getActivity().getApplicationContext(), qtyItem);

		spinner.setAdapter(qtyAdapter);
		spinner.setSelection(cartmap.quantity - 1);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				QuantityDrawerItem item = (QuantityDrawerItem) spinner
						.getSelectedItem();
				sessionManager.updateCart(item_id, item.getTitle());
				cartmap.quantity = Integer.parseInt(item.getTitle());
				getActivity().invalidateOptionsMenu();
				updatePrices();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}

		});
		return spinner;
	}
	
	public void updateCartList() {
		cartlist = TasteryObject.getContext().cartList;
		cartAdapter = new TasteryCartAdapter(getActivity()
				.getApplicationContext(), R.layout.cart_item, cartlist);
		listCart.setAdapter(cartAdapter);
		cartAdapter.notifyDataSetChanged();
		
		listCart.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				showCartDialog(position);
			}
		});
		
		double totalAmount = 0;
		for(CartMap cartItem : cartlist) {
			String price = cartItem.price.replace("₱", "");
			double amount = Double.parseDouble(price);
			totalAmount = totalAmount + amount;
		}
		
		double vat = totalAmount - (totalAmount / 1.12);
        
        TextView vattext = (TextView) rootView.findViewById(R.id.textView1);
		TextView totalText = (TextView) rootView.findViewById(R.id.textView2);
		
        vattext.setText("VAT: ₱" + String.format("%.2f", vat));
		totalText.setText("Total: ₱" + String.format("%.2f", totalAmount));
	}
	
	public void showCartDialog(final int selectedIndex) {
		cartDialog = new Dialog(FragmentCart.this.getActivity(),
				R.style.FoodDialog);
		cartDialog.setContentView(R.layout.dialog_update_cart);
		cartDialog.show();
		
		final EditText txtQuantity = (EditText)cartDialog.findViewById(R.id.txtQuantity);
		final Spinner spDeliveryTime = (Spinner)cartDialog.findViewById(R.id.spDeliveryTime);
		final Button btnUpdateCartItem = (Button)cartDialog.findViewById(R.id.btnUpdateCartItem);
		
		cartItem = TasteryObject.getContext().cartList.get(selectedIndex);
		txtQuantity.setText(""+cartItem.quantity);
		
		List<String> list = new ArrayList<String>();
        list.add("11am - 1pm");
        list.add("3pm - 5pm");
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
		(getActivity(), android.R.layout.simple_spinner_item,list);
		
		spDeliveryTime.setAdapter(dataAdapter);
		spDeliveryTime.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapter, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapter) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btnUpdateCartItem.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				cartItem.quantity = Integer.parseInt(txtQuantity.getText().toString());
				cartItem.deliver_time = spDeliveryTime.getSelectedItem().toString();
				TasteryObject.getContext().cartList.set(selectedIndex, cartItem);
				updateCartList();
				cartDialog.dismiss();
			}
		});
	}
}