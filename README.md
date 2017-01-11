[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Context--Menu.Android-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1387) [![Yalantis](https://github.com/Yalantis/Context-Menu.Android/blob/master/badge.png)](https://yalantis.com/?utm_source=github)

# ContextMenu 

#### You can easily add awesome animated context menu to  your app. 

Check this [project on dribbble] (https://dribbble.com/shots/1785274-Menu-Animation-for-Additional-Functions?list=users&offset=17)

Check this [project on Behance] (https://www.behance.net/gallery/20411445/Mobile-Animations-Interactions)  

![ContextMenu](https://d13yacurqjgara.cloudfront.net/users/125056/screenshots/1785274/99miles-profile-light_1-1-4.gif)

### Usage:

*For a working implementation, have a look at the ```app``` module*

#### 	1. Clone repository and add sources into your project or use Gradle: 
``` compile 'com.yalantis:contextmenu:1.0.7' ```
#### 	2. Create list of `MenuObject`, which consists of icon or icon and description.
You can use any `resource, bitmap, drawable, color` as image:  
```
    item.setResource(...)  
    item.setBitmap(...)  
    item.setDrawable(...)  
    item.setColor(...)  
   ```
You can set image `ScaleType`:  
```
    item.setScaleType(ScaleType.FIT_XY)
```
You can use any `resource, drawable, color` as background: 
```
    item.setBgResource(...)
    item.setBgDrawable(...)
    item.setBgColor(...)
```
Now You can easily add text appearance style for menu titles: 
```
	In your project styles create style for text appearance
	(For better visual effect extend it from TextView.DefaultStyle):
	
	 <style name="TextViewStyle" parent="TextView.DefaultStyle">
        <item name="android:textStyle">italic|bold</item>
        <item name="android:textColor">#26D0EB</item>
	 </style>

And set it's id to your MenuObject :	
    
        MenuObject addFr = new MenuObject("Add to friends");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.icn_3));
        addFr.setDrawable(bd);
        addFr.setMenuTextAppearanceStyle(R.style.TextViewStyle);

```
You can set any `color` as divider color: 
```
    item.setDividerColor(...)
```
Example:  
```
    MenuObject close = new MenuObject();
    close.setResource(R.drawable.icn_close);

    MenuObject send = new MenuObject("Send message");
    send.setResource(R.drawable.icn_1);

    List<MenuObject> menuObjects = new ArrayList<>();
    menuObjects.add(close);
    menuObjects.add(send);
```

####	3. Create `newInstance` of `ContextMenuDialogFragment`, which received `MenuParams` object.

```
    MenuParams menuParams = new MenuParams();
    menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
    menuParams.setMenuObjects(getMenuObjects());
    menuParams.setClosableOutside(true);
    // set other settings to meet your needs
    mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
```

####	4. Set menu with button, which will open `ContextMenuDialogFragment`.

```
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                mMenuDialogFragment.show(fragmentManager, "ContextMenuDialogFragment");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
```

####	5. Implement `OnMenuItemClickListener` interface with `onMenuItemClick` method.
	
```	
public class MainActivity extends ActionBarActivity implements OnMenuItemClickListener
…
    @Override
    public void onMenuItemClick(View clickedView, int position) {
         //Do something here
    }
…
    mMenuDialogFragment.setItemClickListener(this);
```

## Customization: 
For better experience menu item size should be equal to `ActionBar` height.

`newInstance` of `ContextMenuDialogFragment` receives `MenuParams` object that has the fields:

`mMenuObjects` - list of MenuObject objects,

`mAnimationDelay` - delay in millis after fragment opening and before closing, which will make animation smoother on slow devices,

`nAnimationDuration` - duration of every piece of animation in millis,

`isClosableOutside` - if menu can be closed on touch to non-button area,

`isFitSystemWindows` - if true, then the default implementation of fitSystemWindows(Rect) will be executed,

`isClipToPadding` - true to clip children to the padding of the group, false otherwise.

The last two parameters may be useful if you use _Translucent_ parameters in your theme:
```
    <item name="android:windowTranslucentStatus">true</item>
```
To stay `Context Menu` below Status Bar set `fitSystemWindows` to true and `clipToPadding` to false.

## Compatibility
  
  * Android Honeycomb 3.0+

# Changelog

### Version: 1.0.7

  * Text in menu now also clickable
  * Support libs and gradle updated

### Version: 1.0.6

  * com.android.tools.build:gradle:1.5.0
  * Support libs and sdk updated to 23 

### Version: 1.0.5

  * Fixed `setClosableOutside` [issue](https://github.com/Yalantis/Context-Menu.Android/issues/25).
  * Fixed `setAnimationDuration` doesn´t work for open event [issue](https://github.com/Yalantis/Context-Menu.Android/issues/22).
  * Fixed menu item listener setting mechanism. It can be not activity but any class that implements listeners now. [Issue](https://github.com/Yalantis/Context-Menu.Android/issues/24). Attention! You have to set listeners to the context fragment manually. Check block 5 in the `Usage`.

### Version: 1.0.4

  * Old `ContextMenuDialogFragment` `newInstance` methods are deprecated. Use new universal one that received `MenuParams`.
  * Added possibility to dismiss menu by clicking on non-button area. See `MenuParams.setClosableOutside(boolean)`.

### Version: 1.0.3

  * Added menu text appearence style. (Note: other text style methods are deprecated).

### Version: 1.0.2

  * Changed `MenuObject` constructors. Image setting is moved to methods
  * Added styling of `MenuObject` image, background, text color, divider color
  * Added possibility to interact with translucent Status Bar

### Version: 1.0.1

  * Added `OnMenuItemLongClickListener` (usage: the same as `OnMenuItemClickListener`, check sample app)
  * Renamed:
```
com.yalantis.contextmenu.lib.ContextMenuDialogFragment.ItemClickListener ->
com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener

com.yalantis.contextmenu.lib.ContextMenuDialogFragment.ItemClickListener.onItemClick(...) ->
com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener.onMenuItemClick(...)
```

### Version: 1.0

  * Pilot version

#### Let us know!

We’d be really happy if you sent us links to your projects where you use our component. Just send an email to github@yalantis.com And do let us know if you have any questions or suggestion regarding the animation. 

P.S. We’re going to publish more awesomeness wrapped in code and a tutorial on how to make UI for Android (iOS) better than better. Stay tuned!

## License

    Copyright 2017, Yalantis

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
