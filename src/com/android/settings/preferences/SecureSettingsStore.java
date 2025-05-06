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

import android.content.ContentResolver;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.PreferenceDataStore;

public class SecureSettingsStore extends PreferenceDataStore {
    private ContentResolver mContentResolver;

    public SecureSettingsStore(ContentResolver contentResolver) {
        mContentResolver = contentResolver;
    }

    @Override
    public void putString(String key, String value) {
        Settings.Secure.putString(mContentResolver, key, value);
    }

    @Override
    public void putInt(String key, int value) {
        Settings.Secure.putInt(mContentResolver, key, value);
    }

    @Override
    public void putLong(String key, long value) {
        Settings.Secure.putLong(mContentResolver, key, value);
    }

    @Override
    public void putFloat(String key, float value) {
        Settings.Secure.putFloat(mContentResolver, key, value);
    }

    @Override
    public void putBoolean(String key, boolean value) {
        putInt(key, value ? 1 : 0);
    }

    @Override
    public String getString(String key, String defaultValue) {
        String value = Settings.Secure.getString(mContentResolver, key);
        return value == null ? defaultValue : value;
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return Settings.Secure.getInt(mContentResolver, key, defaultValue);
    }

    @Override
    public long getLong(String key, long defaultValue) {
        return Settings.Secure.getLong(mContentResolver, key, defaultValue);
    }

    @Override
    public float getFloat(String key, float defaultValue) {
        return Settings.Secure.getFloat(mContentResolver, key, defaultValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return getInt(key, defaultValue ? 1 : 0) != 0;
    }
}
