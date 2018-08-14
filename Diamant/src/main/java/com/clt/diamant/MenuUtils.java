package com.clt.diamant;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import com.clt.gui.CmdButton;
import com.clt.gui.Commands;
import com.clt.gui.GUI;
import com.clt.gui.Images;
import com.clt.gui.OptionPane;
import com.clt.gui.WindowUtils;
import com.clt.gui.menus.CmdCheckBoxMenuItem;
import com.clt.gui.menus.CmdMenu;
import com.clt.gui.menus.CmdMenuBar;
import com.clt.gui.menus.CmdMenuItem;
import com.clt.gui.menus.MenuCommander;
import com.clt.gui.menus.MenuOwner;
import com.clt.mac.RequiredEventHandler;
import com.clt.script.exp.DefaultFunctionDescriptor;
import com.clt.script.exp.Expression;
import com.clt.script.exp.FunctionDescriptor;
import com.clt.script.exp.Type;
import com.clt.script.exp.Value;
import com.clt.script.exp.types.ListType;
import com.clt.script.exp.types.StructType;
import com.clt.script.exp.types.TypeVariable;
import com.clt.script.exp.values.ListValue;
import com.clt.script.exp.values.StringValue;
import com.clt.util.Misc;
import com.clt.util.Platform;

/**
 * @author dabo
 *
 */
public class MenuUtils {

    private static JFrame helpWindow = null;

    public static CmdMenu addFileMenu(CmdMenuBar mbar, final RequiredEventHandler systemEventHandler) {

        CmdMenu m = mbar.addMenu(Resources.getString("FileMenu"));

        m.addItem(Resources.getString("New"), Commands.cmdNew, KeyEvent.VK_N);
        // m.addItem(Resources.getString("NewExperiment"), cmdNewEx,
        // KeyStroke.getKeyStroke(
        // KeyEvent.VK_N, (m.getToolkit().getMenuShortcutKeyMask() |
        // KeyEvent.SHIFT_MASK)));

        m.addItem(Resources.getString("Open") + "...", Commands.cmdOpen,
                KeyEvent.VK_O);

        if (systemEventHandler != null) {
            MenuUtils.addMRU(m.addSubMenu(Resources.getString("MRU")),
                    systemEventHandler);
        }

        // m.addItem(Resources.getString("Import") + "...", Commands.cmdImport);
        m.addItem(Resources.getString("Close"), Commands.cmdClose, KeyEvent.VK_W);
        m.addSeparator();
        m.addItem(Resources.getString("Save"), Commands.cmdSave, KeyEvent.VK_S);
        m
                .addItem(
                        Resources.getString("SaveAs") + "...",
                        Commands.cmdSaveAs,
                        KeyStroke
                                .getKeyStroke(
                                        KeyEvent.VK_S,
                                        (m.getToolkit().getMenuShortcutKeyMask() | InputEvent.SHIFT_MASK)));
        m.addItem(Resources.getString("Revert"), Commands.cmdRevert);
        m.addSeparator();
        m.addItem(Resources.getString("Print") + "...", Commands.cmdPrint,
                KeyEvent.VK_P);
        if (Platform.showQuitMenuItem()) {
            m.addSeparator();
            if (Platform.isWindows()) {
                m.addItem(Resources.getString("Exit"), Commands.cmdQuit, KeyEvent.VK_Q);
            } else {
                m.addItem(Resources.getString("Quit"), Commands.cmdQuit, KeyEvent.VK_Q);
            }
        }

        return m;
    }

    public static void addMRU(JMenu mru,
            final RequiredEventHandler systemEventHandler) {

        final File lastUsedFile = Preferences.getPrefs().lastUsedFile.getValue();
        if (lastUsedFile != null) {
            mru.add(new CmdMenuItem(lastUsedFile.getName(), new Runnable() {

                public void run() {

                    systemEventHandler.handleOpenFile(lastUsedFile);
                }
            }));
        }
        for (final File f : Preferences.getPrefs().additional_mru) {
            mru.add(new CmdMenuItem(f.getName(), new Runnable() {

                public void run() {

                    systemEventHandler.handleOpenFile(f);
                }
            }));
        }
    }

    public static CmdMenu addEditMenu(CmdMenuBar mbar) {

        CmdMenu m = mbar.addMenu(Resources.getString("EditMenu"));
        m.addItem(Resources.getString("Undo"), Commands.cmdUndo, KeyEvent.VK_Z);
        m.addItem(Resources.getString("Redo"), Commands.cmdRedo, KeyStroke
                .getKeyStroke(
                        KeyEvent.VK_Z,
                        (m.getToolkit().getMenuShortcutKeyMask() | InputEvent.SHIFT_MASK)));
        m.addSeparator();
        m.addItem(Resources.getString("Cut"), Commands.cmdCut, KeyEvent.VK_X);
        m.addItem(Resources.getString("Copy"), Commands.cmdCopy, KeyEvent.VK_C);
        m.addItem(Resources.getString("Paste"), Commands.cmdPaste, KeyEvent.VK_V);
        m.addItem(Resources.getString("Delete"), Commands.cmdDelete);
        m.addItem(Resources.getString("SelectAll"), Commands.cmdSelectAll,
                KeyEvent.VK_A);

        m.addSeparator();

        CmdMenu toolbars = m.addSubMenu(Resources.getString("Toolbars"));
        toolbars.add(Preferences.getPrefs().showToolbox.createMenuItem());
        toolbars.add(Preferences.getPrefs().showProcedureTree.createMenuItem());
        toolbars.add(Preferences.getPrefs().showNodePanel.createMenuItem());

        if (!Platform.isMac()) {
            m.addItem(Resources.getString("Preferences") + "...",
                    Commands.cmdPreferences);
        }
        return m;
    }

    private static void addLanguageItem(CmdMenu m, final String language,
            final Locale locale,
            final MenuOwner owner) {

        return;
    }

    public static CmdMenu addHelpMenu(final CmdMenuBar mbar) {

        MenuOwner owner = new MenuOwner() {

            public void updateMenus() {

                mbar.updateMenus();
            }
        };

        MenuCommander commander = new MenuCommander() {

            public boolean menuItemState(int cmd) {

                return true;
            }

            public String menuItemName(int cmd, String oldName) {

                return oldName;
            }

            public boolean doCommand(int cmd) {

                switch (cmd) {
                    case cmdAbout:
                        Version.showAboutDialog();
                        break;

                    case Main.cmdHelpFunctions:
                        MenuUtils.showHelp();
                        break;

                    case Main.cmdTest:
                        Value[] names = new Value[100000];
                        int maxLength = 4;
                        for (int i = 0; i < names.length; i++) {
                            StringBuilder b = new StringBuilder();
                            for (int j = 0; j < maxLength + 1; j++) {
                                b.append((char) ('a' + Math.round(Math.random()
                                        * ('z' - 'a'))));
                            }
                            names[i] = new StringValue(b.toString());
                        }
                        ListValue words = new ListValue(names);
                        long time = System.currentTimeMillis();
                        SingleDocument.getPrefixLetters(words, "", maxLength);
                        time = System.currentTimeMillis() - time;
                        OptionPane.message(null, time + "ms");
                        break;

                    default:
                        return false;
                }

                return true;
            }
        };

        CmdMenu m
                = new CmdMenu(Resources.getString("HelpMenu"), Commands.cmdMenu, commander);

        if (!Platform.isMac()) {
            m.addItem(Resources.getString("About") + " " + Version.PRODUCT_NAME
                    + "...",
                    Commands.cmdAbout);
            m.addSeparator();
            mbar.add(m);
        }

        m.addItem(Resources.getString("BuiltinFunctions"), Main.cmdHelpFunctions);

        // m.addItem("Test", cmdTest);
        if (Version.DEBUG) {
            JMenuItem item = new JMenuItem("Show active threads");
            item.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {

                    Misc.dumpThreads();
                }
            });
            m.add(item);
        }

        mbar.add(m);
        try {
            mbar.setHelpMenu(m);
        } catch (ThreadDeath d) {
            throw d;
        } catch (Throwable t) {
        }

        return m;
    }

    private static void showHelp() {

        if (MenuUtils.helpWindow == null) {
            JPanel labels = new JPanel(new GridLayout(0, 1));

            labels.add(new JLabel("<html><b>Builtin scripting functions:<b></html>"));
            String[] functions = Expression.getBuiltInFunctions(true);
            for (int i = 0; i < functions.length; i++) {
                labels.add(new JLabel(functions[i]));
            }

            labels.add(new JLabel(""));
            labels.add(new JLabel("<html><b>" + Version.PRODUCT_NAME
                    + " functions:<b><html>"));

            FunctionDescriptor[] fs
                    = new FunctionDescriptor[]{
                        new DefaultFunctionDescriptor("getModelName", Type.String,
                                new Type[0]),
                        new DefaultFunctionDescriptor("getModelPath", Type.String,
                                new Type[0]),
                        new DefaultFunctionDescriptor("getGrammar", Type.String,
                                new Type[]{Type.String}),
                        new DefaultFunctionDescriptor("rpc", new TypeVariable(),
                                new Type[]{
                                    DeviceValue.TYPE, Type.String}, true),
                        new DefaultFunctionDescriptor("getLetters", new ListType(
                                Type.String),
                                new Type[]{new ListType(Type.String), Type.String,
                                    Type.Int}),
                        new DefaultFunctionDescriptor("capitalize", new ListType(
                                Type.String),
                                new Type[]{new ListType(Type.String)}),
                        new DefaultFunctionDescriptor("getNBestList",
                                new ListType(),
                                new Type[]{new StructType()})};

            for (int i = 0; i < fs.length; i++) {
                labels.add(new JLabel(fs[i].getDescription(true)));
            }

            JScrollPane jsp = new JScrollPane(labels);
            jsp.setPreferredSize(new Dimension(250, 200));

            MenuUtils.helpWindow
                    = new JFrame(Resources.getString("BuiltinFunctions"));
            MenuUtils.helpWindow
                    .setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            JPanel content = new JPanel(new BorderLayout(6, 6));
            content.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
            content.add(jsp, BorderLayout.CENTER);
            JButton ok = new CmdButton(new Runnable() {

                public void run() {

                    MenuUtils.helpWindow.setVisible(false);
                }
            }, GUI.getString("OK"));
            content.add(GUI.createButtonPanel(new JButton[]{ok}),
                    BorderLayout.SOUTH);
            MenuUtils.helpWindow.getContentPane().add(content);
            MenuUtils.helpWindow.pack();
            WindowUtils.setLocation(MenuUtils.helpWindow,
                    WindowUtils.CENTER_ON_PARENT);
        }
        MenuUtils.helpWindow.setVisible(true);
    }

    static void hideHelp() {

        if (MenuUtils.helpWindow != null) {
            MenuUtils.helpWindow.setVisible(false);
        }
    }
}
