/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clt.dialogos.sphinx;

import com.clt.diamant.Device;
import com.clt.diamant.Resources;
import com.clt.diamant.graph.nodes.AbstractInputNode;
import com.clt.diamant.graph.nodes.NodeExecutionException;
import com.clt.speech.SpeechException;
import com.clt.speech.recognition.*;

import java.util.List;

/**
 * @author koller
 */
public class SphinxNode extends AbstractInputNode {

    private static Device sphinxDevice = new Device(Resources.getString("Sphinx"));

    public SphinxNode() {
//        this.setProperty(THRESHOLD, new Long(40)); // TODO put this back in
    }

    private Sphinx getRecognizer() {
        return Plugin.getRecognizer();
    }

    @Override protected RecognitionExecutor createRecognitionExecutor(com.clt.srgf.Grammar recGrammar) {
        try {
            this.getRecognizer().stopRecognition();
        } catch (SpeechException exn) {
            throw new NodeExecutionException(this, Resources
                    .getString("RecognizerError")
                    + ".", exn);
        }
        return new SphinxRecognitionExecutor(getRecognizer(), getSettings());
    }

    @Override
    protected Device getDevice() {
        return sphinxDevice;
    }

    @Override
    protected List<LanguageName> getAvailableLanguages() {
        return getSettings() != null ? getSettings().getLanguages() : Plugin.getAvailableLanguages();
    }

    @Override
    protected LanguageName getDefaultLanguage() {
        assert this.getGraph() != null : "must not query default language when plugin settings are unreachable";
        return getSettings().getDefaultLanguage();
    }

    private Settings getSettings() {
        if (getGraph() != null && getGraph().getOwner() != null)
            return ((Settings) getGraph().getOwner()
                .getPluginSettings(Plugin.class));
        else
            return null;
    }
}
