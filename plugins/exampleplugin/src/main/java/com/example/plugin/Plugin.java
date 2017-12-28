package com.example.plugin;

import com.clt.dialogos.plugin.PluginRuntime;
import com.clt.dialogos.plugin.PluginSettings;
import com.clt.diamant.IdMap;
import com.clt.event.ProgressListener;
import com.clt.gui.Images;
import com.clt.xml.XMLReader;
import com.clt.xml.XMLWriter;
import org.xml.sax.SAXException;

import java.awt.Component;
import java.io.File;
import javax.swing.*;


/**
 * Created by max on 20.04.17.
 */
public class Plugin implements com.clt.dialogos.plugin.Plugin {

    @Override
    public void initialize() {
        System.out.println("Hello Example Plugin!");
    }

    @Override
    public String getId() {
        return "dialogos.plugin.example";
    }

    @Override
    public String getName() {
        return "DialogOS Example Plugin";
    }

    @Override
    public Icon getIcon() {
        return Images.load(this, "Lego.png");
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public PluginSettings createDefaultSettings() {
        return new Settings();
    }
}
