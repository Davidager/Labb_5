package Labb_5_package;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by David on 04-Dec-16.
 */
public class Model {
    private URL currentURL;
    private List<URL> history;

    private Stack<URL> backwardStack;
    private Stack<URL> forwardStack;

    public Model() {
        history = new ArrayList<>();

        backwardStack = new Stack<>();
        forwardStack = new Stack<>();
        currentURL = null;

    }

    public URL[] getHistoryArray() {
        Object[] objArray = history.toArray();
        URL[] retArray = new URL[objArray.length];
        int i=objArray.length-1;   // reverserar listan så vi får senaste element först
        for (Object item : objArray) {
            retArray[i] = (URL)item;
            i--;
        }
        return retArray;
    }

    public URL getCurrentURL() {
        return currentURL;
    }

    public Boolean[] getButtonStatus() {   //returnar true om knapparna ska vara igång
        Boolean[] retArray = new Boolean[2];
        retArray[0] = !backwardStack.isEmpty();
        retArray[1] = !forwardStack.isEmpty();
        return retArray;
    }

    public void moveForward() {
        backwardStack.push(currentURL);
        currentURL = forwardStack.pop();
    }

    public void moveBackward() {
        forwardStack.push(currentURL);
        currentURL = backwardStack.pop();
    }

    public void moveToNewPage(URL newURL) {
        if(!history.contains(newURL)) {
            history.add(newURL);   // för historik
        }
        if(currentURL!=null) {
            backwardStack.push(currentURL);
        }
        forwardStack = new Stack<>();
        currentURL = newURL;

    }
}
