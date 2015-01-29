[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Context--Menu.Android-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1387)

# ContextMenu 

#### You can easily add awesome animated context menu to  your app. Made in [Yalantis] (http://yalantis.com/)

Check this [project on dribbble] (https://dribbble.com/shots/1785274-Menu-Animation-for-Additional-Functions?list=users&offset=17)

Check this [project on Behance] (https://www.behance.net/gallery/20411445/Mobile-Animations-Interactions)  

![ContextMenu](https://d13yacurqjgara.cloudfront.net/users/125056/screenshots/1785274/99miles-profile-light_1-1-4.gif)

### Usage:

*For a working implementation, have a look at the ```app``` module*

#### 	1. Clone repository and add sources into your project or use Gradle: 
``` compile 'com.yalantis:contextmenu:1.0.1' ```  
#### 	2. Create list of `MenuObject`, which consists of icon or icon and description.

```
        ArrayList<MenuObject> menuObjects = new ArrayList<>();
        menuObjects.add(new MenuObject(R.drawable.icn_close));
        menuObjects.add(new MenuObject(R.drawable.icn_1, "Send message"));
        menuObjects.add(new MenuObject(R.drawable.icn_2, "Like profile"));
        menuObjects.add(new MenuObject(R.drawable.icn_3, "Add to friends"));
        menuObjects.add(new MenuObject(R.drawable.icn_4, "Add to favorites"));
        menuObjects.add(new MenuObject(R.drawable.icn_5, "Block user"));
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

`animationDuration` - duration of every piece of animation in millis. 

## Compatibility
  
  * Android Honeycomb 3.0+

# Changelog

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
