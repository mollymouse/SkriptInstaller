/*
 * 	SkriptInstaller
 *  By nfell2009
 *  All rights reserved.
 * 
 */

package uk.nfell2009.skriptinstaller;
import java.io.File;
import java.io.IOException;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.Files;


public class Main extends JavaPlugin implements Listener {
	public void onEnable() {
		if (!getDataFolder().exists())
		    getDataFolder().mkdir();
		// Deleting files
		Integer num2Delete = getConfig().getInt("number_of_deletions");
		File file2Delete = null;
		if (num2Delete > 0) {
			for (int i = 1; i <= num2Delete; ++i) {
				file2Delete = new File("plugins/Skript/scripts/" + getConfig().getString("files.delete." + i)); 
				file2Delete.delete();
			}
		}
		
		// Saving files
		Integer num2Save = getConfig().getInt("number_of_files");
		File f = null;
		File f2 = null;
		if (num2Save > 0) {
			for (int i = 1; i <= num2Save; ++i) {
				this.saveResource(getConfig().getString("files.save." + i), true);
				
				// Moving files
				f = new File(getDataFolder(), getConfig().getString("files.save." + i));
				f2 = new File("plugins/Skript/scripts/" + getConfig().getString("files.save." + i));
				try {
					Files.move(f, f2);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		// Addons
		
		Integer num2Move = getConfig().getInt("number_of_addons");
		File addon = null;
		if (num2Move > 0) {
			for (int i = 1; i <= num2Move; ++i) {
				this.saveResource(getConfig().getString("files.addons." + i), true);
				
				// Moving files
				f = new File(getDataFolder(), getConfig().getString("files.addons." + i));
				f2 = new File("plugins/Updater/" + getConfig().getString("files.addons." + i));
				addon = new File("plugins/" + getConfig().getString("files.addons." + i));
				if (addon.exists()) {
					if (getConfig().getBoolean("files.addons." + i + "_replace") == true) {
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
			}
		}
		
		// Deleting data folder
		f = new File(getDataFolder(), "");
		f.delete();
	}
}
