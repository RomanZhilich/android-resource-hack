/* This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details. */
package tk.dweller.resourcehack;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Date: 5/27/13
 */
public class ResourceHackActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_hack);

        final TextView before = (TextView) findViewById(R.id.before);
        final TextView after = (TextView) findViewById(R.id.after);

        before.setText(getString(R.string.before, initialStringValue()));

        final String resourceName = getResources().getResourceEntryName(R.string.initial_value);
        redefineStringResourceId(resourceName, R.string.evil_value);

        after.setText(getString(R.string.after, initialStringValue()));
    }

    private String initialStringValue() {
        return getString(R.string.initial_value);
    }

    /**
     * Pure evil way to change resource value at runtime.
     * In order to work, this approach requires final static field int field to be initialized as Integer.valueOf
     * In this case it is done with Ant.
     */
    protected void redefineStringResourceId(final String resourceName, final int newId) {
        try {
            final Field field = R.string.class.getDeclaredField(resourceName);
            field.setAccessible(true);
            field.set(null, newId);
        } catch (Exception e) {
            Log.e(getClass().getName(), "Couldn't redefine resource id", e);
        }
    }
}
