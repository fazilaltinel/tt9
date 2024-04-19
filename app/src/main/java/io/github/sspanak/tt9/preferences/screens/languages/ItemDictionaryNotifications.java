package io.github.sspanak.tt9.preferences.screens.languages;

import androidx.preference.Preference;
import androidx.preference.SwitchPreferenceCompat;

import io.github.sspanak.tt9.preferences.PreferencesActivity;
import io.github.sspanak.tt9.util.Permissions;

public class ItemDictionaryNotifications {
	public static final String NAME = "dictionary_notifications";

	private final SwitchPreferenceCompat item;
	private final Permissions permissions;


	public ItemDictionaryNotifications(SwitchPreferenceCompat preference, PreferencesActivity activity) {
		this.item = preference;
		this.permissions = new Permissions(activity);
	}


	public ItemDictionaryNotifications populate() {
		if (item == null) {
			return this;
		}

		boolean noPermission = permissions.noPostNotifications();
		item.setVisible(noPermission);
		((SwitchPreferenceCompat) item).setChecked(!noPermission);

		return this;
	}


	public ItemDictionaryNotifications enableClickHandler() {
		if (item != null) {
			item.setOnPreferenceChangeListener(this::onClick);
		}

		return this;
	}


	protected boolean onClick(Preference p, Object value) {
		if (value == Boolean.TRUE || permissions.noPostNotifications()) {
			permissions.requestPostNotifications();
		}

		// Switch off the component on user refusal. Android will not allow permission request again.
		item.setEnabled(false);
		return !permissions.noPostNotifications();
	}
}
