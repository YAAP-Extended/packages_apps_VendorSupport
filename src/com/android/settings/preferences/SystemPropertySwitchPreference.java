/*
 * Copyright (C) 2016-2019 crDroid Android Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.settings.preferences;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.preference.SwitchPreference;

import com.android.settings.utils.AdaptivePreferenceUtils;

public class SystemPropertySwitchPreference extends SwitchPreference {
    private boolean mShouldRemove = false;
    private String mSystemProperty;

    public SystemPropertySwitchPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public SystemPropertySwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SystemPropertySwitchPreference(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        int layoutRes = AdaptivePreferenceUtils.getLayoutResourceId(context, attrs);
        if (layoutRes != -1) {
            setLayoutResource(layoutRes);
        }
        setShouldDisableView(true);
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        boolean value = SystemProperties.getBoolean(mSystemProperty, (Boolean) defaultValue);
        setChecked(value);
    }

    public void setSystemProperty(String property) {
        mSystemProperty = property;
    }

    @Override
    public void setChecked(boolean value) {
        super.setChecked(value);
        SystemProperties.set(mSystemProperty, value ? "1" : "0");
    }

    public void setShouldRemove(boolean remove) {
        mShouldRemove = remove;
    }

    public boolean shouldRemove() {
        return mShouldRemove;
    }

    private static class SystemProperties {
        public static boolean getBoolean(String key, boolean defValue) {
            String value = get(key);
            return TextUtils.isEmpty(value) ? defValue : Boolean.parseBoolean(value);
        }

        public static String get(String key) {
            try {
                Class<?> systemProperties = Class.forName("android.os.SystemProperties");
                return (String) systemProperties.getMethod("get", String.class).invoke(null, key);
            } catch (Exception e) {
                return null;
            }
        }

        public static void set(String key, String value) {
            try {
                Class<?> systemProperties = Class.forName("android.os.SystemProperties");
                systemProperties.getMethod("set", String.class, String.class).invoke(null, key, value);
            } catch (Exception e) {
                // Do nothing
            }
        }
    }
}
