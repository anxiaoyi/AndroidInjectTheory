package com.zk.injectdemo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

public class InjectHelper {

	/**
	 * return inject help info
	 * 
	 * @param c
	 * @param declared
	 * @return
	 */
	public static String initViewIdInject(Activity activity, boolean declared) {
		StringBuilder sb = new StringBuilder(activity.getClass()
				.getSimpleName());

		Field[] field = null;
		if (declared) {
			field = activity.getClass().getDeclaredFields();
			sb.append(" DeclaredFields:").append("<br><br>");
		} else {
			field = activity.getClass().getFields();
			sb.append(" Fields:").append("<br><br>");
		}

		if (field != null && field.length > 0) {
			for (Field f : field) {
				final ViewInject viewInject = f.getAnnotation(ViewInject.class);

				boolean hasViewInject = false;

				if (viewInject != null) {
					hasViewInject = true;
					int viewId = viewInject.id();
					try {
						if (declared) {
							f.setAccessible(true);
						}
						f.set(activity, activity.findViewById(viewId));
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				}

				if (hasViewInject) {
					sb.append("<br>").append(
							getColoredText(f.getName() + ": Inject"));
				} else {
					sb.append("<br>").append(f.getName() + ": Not Inject");
				}
			}
		}

		return sb.toString();
	}

	/**
	 * 
	 * @param activity
	 * @param declared
	 * @param params
	 *            View
	 * @return
	 */
	public static String initViewMethodInject(final Activity activity,
			boolean declared, final boolean hasParam) {
		StringBuilder sb = new StringBuilder(activity.getClass()
				.getSimpleName());

		Field[] field = activity.getClass().getDeclaredFields();

		if (field != null && field.length > 0) {
			for (Field f : field) {
				f.setAccessible(true);

				final ViewInject viewInject = f.getAnnotation(ViewInject.class);
				boolean hasMethodInject = false;

				if (viewInject != null) {

					try {
						int viewId = viewInject.id();
						f.set(activity, activity.findViewById(viewId));

						String onClickMethodName = viewInject.onClick();
						if (!TextUtils.isEmpty(onClickMethodName)) {

							final Method method = hasParam ? (declared ? activity
									.getClass().getDeclaredMethod(
											onClickMethodName, View.class)
									: activity.getClass().getMethod(
											onClickMethodName, View.class))
									: (declared ? activity.getClass()
											.getDeclaredMethod(
													onClickMethodName)
											: activity.getClass().getMethod(
													onClickMethodName));

							if (method != null) {
								if (declared) {
									method.setAccessible(true);
								}
								hasMethodInject = true;

								final View targetView = (View) f.get(activity);
								targetView
										.setOnClickListener(new OnClickListener() {
											@Override
											public void onClick(View arg0) {
												try {
													if (hasParam) {
														method.invoke(activity,
																arg0);
													} else {
														method.invoke(activity);
													}
												} catch (Exception e) {
													e.printStackTrace();
												}
											}
										});
							}

							if (hasMethodInject) {
								sb.append("<br>").append(
										getColoredText(method.getName()
												+ "("
												+ (hasParam ? method
														.getParameterTypes()[0]
														.getSimpleName() : "")
												+ "): Inject"));
							} else {
								sb.append("<br>").append(
										getColoredText(method.getName()
												+ "("
												+ (hasParam ? method
														.getParameterTypes()[0]
														.getSimpleName() : "")
												+ "): Not Inject"));
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		return sb.toString();
	}

	public static String getColoredText(String target) {
		return "<font color=#cc0029>" + target + "</font>";
	}
}
