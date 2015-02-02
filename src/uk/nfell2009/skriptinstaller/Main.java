/*
 * 	SkriptInstaller
 *  By nfell2009
 *  All rights reserved.
 * 
 */

package uk.nfell2009.skriptinstaller;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.Files;


public class Main extends JavaPlugin implements Listener {
	public void onEnable() {
		if (!getDataFolder().exists())
		    getDataFolder().mkdir();
		// Deleting files
		Integer num2Delete = getConfig().getInt("files.delete.files");
		File file2Delete = null;
		if (num2Delete > 0) {
			for (int i = 1; i <= num2Delete; ++i) {
				file2Delete = new File("plugins/Skript/scripts/" + getConfig().getString("files.delete." + i + ".name")); 
				file2Delete.delete();
			}
		}
		
		// Saving files
		Integer num2Save = getConfig().getInt("files.save.files");
		File f = null;
		File f2 = null;
		URL url = null;
		if (num2Save > 0) {
			for (int i = 1; i <= num2Save; ++i) {
				System.out.println(i);
				if (getConfig().getBoolean("files.save." + i + ".local") == true) {
					this.saveResource(getConfig().getString("files.save." + i + ".name"), true);
					// Moving files
					f = new File(getDataFolder(), getConfig().getString("files.save." + i + ".name"));
					f2 = new File("plugins/Skript/scripts/" + getConfig().getString("files.save." + i + ".name"));
					try {
						Files.move(f, f2);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					try {
						url = new URL(getConfig().getString("files.save." + i + ".url"));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					f = new File("plugins/Skript/scripts/" + getConfig().getString("files.save." + i + ".name"));
					try {
						FileUtils.copyURLToFile(url, f);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		
		// Addons
		
		Integer num2Move = getConfig().getInt("files.addons.files");
		File addon = null;
		if (num2Move > 0) {
			for (int i = 1; i <= num2Move; ++i) {
				if (getConfig().getBoolean("files.addons." + i + ".local") == true) {
					this.saveResource(getConfig().getString("files.addons." + i + ".name"), true);
					f = new File(getDataFolder(), getConfig().getString("files.addons." + i + ".name"));
					f2 = new File("plugins/Updater/" + getConfig().getString("files.addons." + i + ".name"));
					addon = new File("plugins/" + getConfig().getString("files.addons." + i + ".name"));
					if (addon.exists()) {
						if (getConfig().getBoolean("files.addons." + i + ".overwrite") == true) {
							try {
								Files.move(f, f2);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					} else {
						try {
							Files.move(f, f2);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} else {
					try {
						url = new URL(getConfig().getString("files.addons." + i + ".url"));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					f = new File("plugins/" + getConfig().getString("files.addons." + i + ".name"));
					try {
						FileUtils.copyURLToFile(url, f);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		}
		
		// Deleting data folder
		f = new File(getDataFolder(), "");
		f.delete();
	}
}
