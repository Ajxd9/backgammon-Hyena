// 
//
//  @ Project : Project Gammon
//  @ File : MainController.java
//  @ Date : 12/12/2012
//  @ Authors : DONG Chuan, BONNETTO Benjamin, FRANCON Adrien, POTHELUNE Jean-Michel
//
//

package com.HyenaBgammon.controller;

import java.awt.Container;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import com.HyenaBgammon.models.ManagerModel;
import com.HyenaBgammon.models.Master;
import com.HyenaBgammon.models.GameParameters;
import com.HyenaBgammon.models.Session;
import com.HyenaBgammon.view.IntermediateGameView;
import com.HyenaBgammon.view.ManagerView;
import com.HyenaBgammon.view.MenuView;
import com.HyenaBgammon.view.RulesView;
import com.HyenaBgammon.view.HistoryView;

public class MainController implements Controller {

    private MenuView viewMenu;
    private Session session;
    private Master master;
    private JFrame frame;
    private IntermediateGameView gameCreation;
    private MainController mainController;
    protected IntermediateGameController gameIntermediateController;
    protected PlayerListController playerListController;
    private static ManagerController managerController;
    private static ManagerView managerView;
    private static ManagerModel managerModel;

    public MainController(Master master) {
        this.master = master;
        mainController = this;
        frame = new JFrame("Backgammon Game");
        session = null;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(810, 600);
        frame.setLocationRelativeTo(null);
        Container panel = frame.getContentPane();
        panel.setLayout(new FlowLayout());

        viewMenu = new MenuView();
        frame.setContentPane(viewMenu);
    
        frame.setVisible(true);

        build();
    }

    public void build() {
        listenerQuitButton();
        listenerNewGameButton();
        listenerResumeGameButton();
        listenerAddButton();
        listenerManageButton();
        listenerHelpButton();
        listenerHistoryButton();
    }

    /**
     * Listener for "New Session" button
     */
    private void listenerNewGameButton() {
        viewMenu.getNewSessionButton().addMouseListener(new MouseListener() {
            
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}        
            @Override
            public void mouseExited(MouseEvent e) {}            
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                viewMenu.setVisible(false);
                gameIntermediateController = new IntermediateGameController(true, mainController);    
            }
        });
    }
    
    private void listenerResumeGameButton() {
        viewMenu.getLoadSessionButton().addMouseListener(new MouseListener() {
            
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}        
            @Override
            public void mouseExited(MouseEvent e) {}            
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                viewMenu.setVisible(false);
                gameIntermediateController = new IntermediateGameController(false, mainController);
            }
        });
    }
    
    private void listenerAddButton() {
        viewMenu.getAddButton().addMouseListener(new MouseListener() {
            
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}        
            @Override
            public void mouseExited(MouseEvent e) {}            
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                viewMenu.setVisible(false);
                playerListController = new PlayerListController(false, mainController);    
            }
        });
    }
    
    /**
     * 
     * listenerQuitButton
     * 
     */
    private void listenerQuitButton() {
        viewMenu.getQuitButton().addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
            }
        });
    }
    
    private void listenerHistoryButton() {
        viewMenu.getHistoryButton().addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                HistoryView historyView = new HistoryView(event -> {
                    frame.setContentPane(viewMenu);
                    frame.revalidate();
                    frame.repaint();
                });
                new HistoryController(historyView);
                frame.setContentPane(historyView);
                frame.revalidate();
                frame.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
        });
    }

    
    private void listenerManageButton() {
        viewMenu.getManageButton().addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
                viewMenu.setVisible(false);
                managerModel = new ManagerModel();
                managerView = new ManagerView();
                managerController = new ManagerController(managerModel, managerView);
                managerView.setVisible(true);
            }
        });
    }
    
    private void listenerHelpButton() {
        viewMenu.getHelpButton().addMouseListener(new MouseListener() {
            
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}        
            @Override
            public void mouseExited(MouseEvent e) {}            
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseClicked(MouseEvent e) {
            	frame.setContentPane(new RulesView(event -> {
                    frame.setContentPane(viewMenu); // Navigate back to the menu
                    frame.revalidate();
                    frame.repaint();
                }));
                frame.revalidate();
                frame.repaint();
            }
        });
    }

    @Override
    public JFrame getFrame() {
        return frame;
    }
    
    @Override
    public void back() {
        frame.setContentPane(viewMenu);
        viewMenu.setVisible(true);
    }
    
    public void newSession(GameParameters gameParameter) {
        master.launchSession(gameParameter);
        session = master.getSession();
        session.StartGame();
        GameController gameController = new GameController(session, this);
        
        frame.setContentPane(gameController.getGameView());
    }
    
    public void endSession() {
        master.stopSession(session.getSessionId());
        //session = master.getSession();
        //session.StartGame();
        //GameController gameController = new GameController(session, this);
        
        //frame.setContentPane(gameController.getGameView());
    }
    
    public void loadSession(Session session) {
        master.addSession(session);
        this.session = session;
        //session.StartGame();
        GameController gameController = new GameController(session, this);
        
        frame.setContentPane(gameController.getGameView());
    }

    @Override
    public Controller getController() {
        return this;
    }

    public IntermediateGameView getGameCreation() {
        return gameCreation;
    }

    public void setGameCreation(IntermediateGameView gameCreation) {
        this.gameCreation = gameCreation;
    }
    
    
}
