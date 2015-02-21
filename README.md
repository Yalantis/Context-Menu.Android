[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Context--Menu.Android-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1387)

# ContextMenu 

#### You can easily add awesome animated context menu to  your app. Made in [Yalantis] (http://yalantis.com/)

Check this [project on dribbble] (https://dribbble.com/shots/1785274-Menu-Animation-for-Additional-Functions?list=users&offset=17)

Check this [project on Behance] (https://www.behance.net/gallery/20411445/Mobile-Animations-Interactions)  

![ContextMenu](https://d13yacurqjgara.cloudfront.net/users/125056/screenshots/1785274/99miles-profile-light_1-1-4.gif)

### Usage:

*For a working implementation, have a look at the ```app``` module*

#### 	1. Clone repository and add sources into your project or use Gradle: 
``` compile 'com.yalantis:contextmenu:1.0.2' ```  
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
You can use any `color` as text color: 
```
    item.setTextColor(...)
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

####	3. Create `newInstance` of `ContextMenuDialogFragment`, which received menu item size and list of `MenuObject`.

```
 mMenuDialogFragment = ContextMenuDialogFragment.newInstance((int) getResources().getDimension(R.dimen.tool_bar_height), menuObjects );
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
```

## Customization: 
For better experience menu item size should be equal to `ActionBar` height.

`newInstance` of `ContextMenuDialogFragment` can receive:

 `animationDelay` - delay in millis after fragment opening and before closing, which will make animation smoother on slow devices,

`animationDuration` - duration of every piece of animation in millis,

`fitSystemWindows` - if true, then the default implementation of fitSystemWindows(Rect) will be executed,

`clipToPadding` - true to clip children to the padding of the group, false otherwise.

The last two parameters may be useful if you use _Translucent_ parameters in your theme:
```
    <item name="android:windowTranslucentStatus">true</item>
```
To stay `Context Menu` below Status Bar set `fitSystemWindows` to true and `clipToPadding` to false.

## Compatibility
  
  * Android Honeycomb 3.0+

# Changelog

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

## License

    Copyright 2015, Yalantis

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
