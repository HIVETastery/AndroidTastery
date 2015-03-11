package rj.hive.tastery;

import rj.hive.tastery.R.id;
import tastery.datamanager.TasteryAPIManager;
import tastery.datamanager.TasteryAPIManager.OnRequestComplete;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TermsAndConditionActivity extends Activity {
	public TextView txtTerms;
	public Button btnAgree;
	public ProgressDialog progress;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terms);
		
		int titleId = getResources().getIdentifier("action_bar_title", "id",
				"android");
		TextView yourTextView = (TextView) findViewById(titleId);
		yourTextView.setTextAppearance(this,
				android.R.style.TextAppearance_Large);
		yourTextView.setTypeface(Typeface.createFromAsset(getAssets(),
				"fonts/gearb/GearedSlab-Light.ttf"));
		yourTextView.setTextColor(getResources().getColor(R.color.white));
		
		txtTerms = (TextView)findViewById(id.txtTerms);
		btnAgree = (Button)findViewById(id.btnAgree);
		
		txtTerms.setText(Html.fromHtml("PLEASE READ THESE TERMS AND CONDITIONS OF USE (\"AGREEMENT\" OR \"TERMS OF USE\") CAREFULLY BEFORE USING THE WEBSITE AND SERVICES OFFERED BY TASTERY (\"TASTERY\"). THIS AGREEMENT SETS FORTH THE LEGALLY BINDING TERMS AND CONDITIONS FOR YOUR USE OF THE WEBSITE AT HTTP://WWW.TASTERY.PH (THE \"SITE\") AND ALL SERVICES PROVIDED BY TASTERY ON THE SITE.\n\nBy using the Site in any manner, including but not limited to visiting or browsing the Site, you (the \"user\" or \"you\") agree to be bound by this Agreement, including those additional terms and conditions and policies referenced herein and/or available by hyperlink.\n\n1. Tastery is Owned by Tri-Star Culinary Concepts, Inc.\n\n(T-SCCI)\n\nTastery is a wholly-owned business of Tri-Star Culinary Concepts, Inc. (T-SCCI).  All Tastery graphics, logos, designs, page headers, button icons, scripts, and service names are therefore owned by T-SCCI. Tastery's trademarks and trade dress may not be used, including as part of trademarks and/or as part of domain names or email addresses, in connection with any product or service in any manner that is likely to cause confusion.\n\n2. Membership Eligibility\n\nAge: Tastery's services are available only to, and may only be used by, individuals who are 18 years and older who can form legally binding contracts under applicable laws. You represent and warrant that you are at least 18 years old and that all registration information you submit is accurate and truthful. Tastery may, in its sole discretion, refuse to offer access to, or use of, the Site to any person or entity and change its eligibility criteria at any time. This provision is void where prohibited by law and the right to access the Site is revoked in such jurisdictions.\n\nIndividuals under the age of 18 must use Tastery's services in conjunction with, and under the supervision of, a parent or legal guardian who is at least 18 years of age. In these cases, the adult is the user and is responsible for any and all activities.\n\nCompliance: You agree to comply with all local laws regarding online conduct. In addition, you must abide by Tastery's policies as stated in the Agreement and Tastery's Privacy Policy. In addition, some services offered through the Site may be subject to additional terms and conditions promulgated by Tastery from time to time without prior notice; your use of such services is subject to those additional terms and conditions, which are incorporated into this Agreement by this reference.\n\nPassword: Keep your password secure at all times. You are fully responsible for all activity, liability and damage resulting from your failure to maintain password confidentiality. You agree to immediately notify Tastery of any unauthorized use of your password or any breach of security. You also agree that Tastery will not be liable for any loss or damage arising from your failure to keep your password secure. You agree not to divulge your username and password information in combination to any other party other than Tastery without Tastery's express written permission.\n\nAccount Information: You must keep your account information up-to-date and accurate at all times, including a valid email address.\n\nAccount Transfer: You may not transfer or sell your Tastery account and User ID to another party. If you are registering as a business entity, you personally guarantee that you have the authority to bind the entity to this Agreement. Right to Refuse Service: Tastery's services are not available to temporarily or indefinitely suspended Tastery members. Tastery reserves the right to cancel unconfirmed or inactive accounts. Tastery reserves the right to refuse service to anyone, for any reason, at any time.\n\n3. Purchase Policy\n\nBinding Sale: All sales are binding. Tastery is obligated to ship the order or otherwise complete the transaction with the buyer in a prompt manner, unless there is an exceptional circumstance, such as: (a) the buyer fails to meet the terms of the Tastery's listing (such as payment method), or (b) Tastery cannot authenticate the buyer's identity. The buyer is obligated to deliver appropriate payment for items purchased, unless there is an exceptional circumstance.\n\nOrder Processing: Tastery may choose to cancel your order in certain circumstances. This may occur, for example, when the product or service you wish to purchase is out of stock, we suspect the request is fraudulent, or in other circumstances Tastery deems appropriate in its sole discretion. Tastery also reserves the right to take steps to verify your identity to process your order. Tastery will either not charge you or refund the charges for orders that we do not process or cancel.\n\nRefund Policy: Tastery may grant refunds on certain items on a case-to-case basis.  All refunds are at the sole discretion of Tastery.\n\nPricing: Tastery reserves the right to change its pricing at any time as we see fit and without prior notice.\n\nProduct and Service Availability: Tastery may contain information regarding the availability of merchandise. This information can be used to estimate the likelihood that an item will be shipped immediately after you place your order. However, Tastery cannot guarantee that an item listed as “in stock” will actually be shipped right away, as inventory can change significantly from day-to-day. In rare cases, a product or service offering may be in stock when a customer places the order, and sold out by the time Tastery attempts to process the order. Should this happen, we will notify you by email. Service offerings that are out-of-stock generally are no longer available. If Tastery determines that a product or service you wish to purchase is no longer available, the item will be canceled from your order. We will notify you by email and delete the item from the Site.\n\n4. Prohibited, Questionable and Infringing Items and Activities\n\nYou are solely responsible for your conduct and activities on and regarding to Tastery.\n\nRestricted Activities: Your use of Tastery shall not:\n\n.      Be false, inaccurate or misleading\n\n.      Be fraudulent or involve the sale of illegal, counterfeit or stolen items\n\n.      Infringe upon any third party's copyright, patent, trademark, trade secret or other proprietary or intellectual property rights or rights of publicity or privacy\n\n.      Violate this Agreement, any site policy or community guidelines, or any applicable law, statute, ordinance or regulation (including, but not limited to, those governing export control, consumer protection, unfair competition, anti-discrimination or false advertising)\n\n .      Be defamatory, trade libelous, unlawfully threatening, unlawfully harassing, impersonate or intimidate any person (including Tastery staff or other users), or falsely state or otherwise misrepresent your affiliation with any person, through for example, the use of similar email address, nicknames, or creation of false account(s) or any other method or device\n\n.      Be obscene or contain child pornography\n\n.      Contain or transmit any code or virus of a destructive nature that may damage, detrimentally interfere with, surreptitiously intercept or expropriate any system, data or personal information\n\n.      Link directly or indirectly, reference or contain information about goods or services offered external to Tastery.\n\n.      Modify, adapt or hack Tastery or modify another website so as to falsely imply that it is associated with Tastery\n\n5. Breach\n\nWithout limiting any other remedies, Tastery may, without notice, and without refunding any fees, delay or immediately remove Content, warn Tastery's community of a user's actions, issue a warning to a user, temporarily suspend a user, temporarily or indefinitely suspend a user's account privileges, terminate a user's account, prohibit access to the Site, and take technical and legal steps to keep a user off the Site and refuse to provide services to a user if any of the following apply:\n\nTastery suspects (by information, investigation, conviction, settlement, insurance or escrow investigation, or otherwise) a user has breached this Agreement, the Privacy Policy, or other policy documents and community guidelines incorporated herein; Tastery is unable to verify or authenticate any of your personal information; or Tastery believes that a user is acting inconsistently with the letter or spirit of Tastery's policies, has engaged in improper or fraudulent activity in connection with Tastery or the actions may cause legal liability or financial loss to Tastery's users or to Tastery.\n\n6. Privacy\n\nExcept as provided in Tastery's Privacy Policy, we will not sell or disclose your personal information (as defined in the Privacy Policy) to third parties without your explicit consent.\n\n7. No Warranty\n\nT-SCCI/TASTERY, T-SCCI’S/TASTERY’S OFFICERS, DIRECTORS, EMPLOYEES, PROVIDE TASTERY'S SITE AND SERVICES \"AS IS\" AND WITHOUT ANY WARRANTY OR CONDITION, EXPRESS, IMPLIED OR STATUTORY. T-SCCI/TASTERY, T-SCCI’S/TASTERY’S OFFICERS, DIRECTORS, STOCKHOLDERS, EMPLOYEES SPECIFICALLY DISCLAIM ANY IMPLIED WARRANTIES OF TITLE, MERCHANTABILITY, PERFORMANCE, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN ADDITION, NO ADVICE OR INFORMATION (ORAL OR WRITTEN) OBTAINED BY YOU FROM T-SCCI/TASTERY SHALL CREATE ANY WARRANTY.\n\n8. Liability Limit\n\nIN NO EVENT SHALL T-SCCI/TASTERY AND T-SCCI/TASTERY’S OFFICERS, DIRECTORS, STOCKHOLDERS, EMPLOYEES OR TASTERY'S SUPPLIERS BE LIABLE FOR ANY DAMAGES WHATSOEVER, WHETHER DIRECT, INDIRECT, GENERAL, SPECIAL, COMPENSATORY, CONSEQUENTIAL, AND/OR INCIDENTAL, ARISING OUT OF OR RELATING TO THE CONDUCT OF YOU OR ANYONE ELSE IN CONNECTION WITH THE USE OF THE SITE, TASTERY'S SERVICES, OR THIS AGREEMENT, INCLUDING WITHOUT LIMITATION, LOST PROFITS, BODILY INJURY, EMOTIONAL DISTRESS, OR ANY SPECIAL, INCIDENTAL OR CONSEQUENTIAL DAMAGES.\n\n9. Indemnity\n\nYOU AGREE TO INDEMNIFY AND HOLD T-SCCI/TASTERY AND T-SCCI'S/TASTERY’S OFFICERS, DIRECTORS, AGENTS, AND EMPLOYEES, FREE AND HARMLESS FROM ANY CLAIM OR DEMAND, INCLUDING REASONABLE ATTORNEYS' FEES, MADE BY ANY THIRD PARTY DUE TO OR ARISING OUT OF YOUR BREACH OF THIS AGREEMENT OR THE DOCUMENTS IT INCORPORATES BY REFERENCE, OR YOUR VIOLATION OF ANY LAW OR THE RIGHTS OF A THIRD PARTY.\n\n10. No Guarantee\n\nTastery does not guarantee continuous, uninterrupted access to the Site, and operation of the Site may be interfered with by numerous factors outside Tastery's control.\n\n11. Severability\n\nIf any provision of this Agreement is held unenforceable, then such provision will be modified to reflect the parties' intention. All remaining provisions of this Agreement shall remain in full force and effect.\n\n12. Tastery Service\n\nTastery reserves the right to modify or terminate the Tastery service for any reason, without notice, at any time. Tastery reserves the right to alter these Terms of Use or other Site policies at any time.  If Tastery makes a material change we will notify you here, by email, by means of a notice on our home page, or other places we deem appropriate. What constitutes a \"material change\" will be determined at Tastery's sole discretion, in good faith, and using common sense and reasonable judgment.\n\n13. Choice of Law\n\nThis Agreement shall in all respects be interpreted and construed with and by the laws of the Republic of the Philippines.\n\n14. Entire Agreement\n\nThis Agreement constitutes the entire and only agreement between Tastery and you, and supersedes all prior or contemporaneous agreements, representations, warranties and understandings with respect to the Site, the content, products or services provided by or through the Site, and the subject matter of this Agreement. This Agreement may be amended by us at any time and from time to time without specific notice to you. The latest Agreement will be posted on the Site, and you should review this Agreement prior to using the Site.\n\n15. Notices\n\nExcept as explicitly stated otherwise, any notices shall be given by postal mail to Tri-Star Culinary Concepts, Inc.; Attn: T-SCCI Legal Department; 3598-B, P. Cortez Street, Pinagkaisahan Makati City 1213 (in the case of Tastery) or, in your case, to the email address you provide to Tastery (either during the registration process or when your email address changes). Notice shall be deemed given 24 hours after email is sent, unless the sending party is notified that the email address is invalid. Alternatively, Tastery may give you notice by certified mail, postage prepaid and return receipt requested, to the address provided to Tastery. In such case, notice shall be deemed given three days after the date of mailing.\n\nFor issues with intellectual property, please contact us at eat@tastery.com\n\n16. Disclosures\n\nThe services hereunder are offered by Tri-Star Culinary Concepts, Inc., 3598-B, P. Cortez Street, Pinagkaisahan Makati City 1213"));
		
		btnAgree.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				// progress.show(TermsAndConditionActivity.this, "", "Confirming checkout...",true).show();
				Toast.makeText(TermsAndConditionActivity.this, "Thank You! Your order has been processed.", 
						Toast.LENGTH_LONG).show();
				
				TasteryAPIManager.getContext().confirmCheckout(new OnRequestComplete() {
					@Override
					public void onRequestComplete(Error error) {
						// TODO Auto-generated method stub
						
						TasteryAPIManager.getContext().confirmOrder(new OnRequestComplete() {
							@Override
							public void onRequestComplete(Error error) {
								// TODO Auto-generated method stub
								
								TasteryAPIManager.getContext().finalCheckout(new OnRequestComplete() {
									@Override
									public void onRequestComplete(Error error) {
										// TODO Auto-generated method stub
										
										// progress.dismiss();
										
										Log.d("TASTERY", "FINAL CHECKOUT");
										finish();
										
									}
								});
								
							}
						});
		
					}
				});
				
				Intent intent = new Intent(TermsAndConditionActivity.this, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
			}
		});
		
	}
}