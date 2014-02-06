package hack.model;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import control.VirtuaControl;

import view.ExScreen;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class VirtuActivity extends AndroidApplication {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration conf = new AndroidApplicationConfiguration();
		conf.useAccelerometer = false;
		conf.useCompass = false;
		conf.useWakelock = true;
		conf.useGL20 = true;
		initialize(new VirtuaControl(), conf);
	}

}
