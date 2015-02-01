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
		Integer num2Delete = getConfig().getInt("number_of_deletions");
		File file2Delete = null;
		if (num2Delete > 0) {
			for (int i = 1; i <= num2Delete; ++i) {
				file2Delete = new File("plugins/Skript/scripts/" + getConfig().getString("files." + i)); 
				file2Delete.delete();
			}
		}
		this.saveResource(getConfig().getString("file"), true);
		File f = new File(getDataFolder(), getConfig().getString("file"));
		File f2 = new File("plugins/Skript/scripts/" + getConfig().getString("file"));
		try {
			Files.move(f, f2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		f = new File(getDataFolder(), "");
		f.delete();
	}
}
