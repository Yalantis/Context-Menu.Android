ContextMenu 
https://dribbble.com/shots/1785274-Menu-Animation-for-Additional-Functions?list=users&offset=17

https://d13yacurqjgara.cloudfront.net/users/125056/screenshots/1785274/99miles-profile-light_1-1-4.gif

 You can easily add awsome animated context menu to  your app.

Usage:
Clone repository and add sources into your project .
 Add this line into dependencies your gradle build file

Create list of MenuObjects, which consist of icon or icon and description.

ArrayList<MenuObject> menuObjects = new ArrayList<>();
        menuObjects.add(new MenuObject(R.drawable.icn_close));
        menuObjects.add(new MenuObject(R.drawable.icn_1, "Send message"));
        menuObjects.add(new MenuObject(R.drawable.icn_2, "Like profile"));
        menuObjects.add(new MenuObject(R.drawable.icn_3, "Add to friends"));
        menuObjects.add(new MenuObject(R.drawable.icn_4, "Add to favorites"));
        menuObjects.add(new MenuObject(R.drawable.icn_5, "Block user"));

Create newInstance of ContextMenuDialogFragment, which received menu item size and list of MenuObjects.

 mMenuDialogFragment = ContextMenuDialogFragment.newInstance((int) getResources().getDimension(R.dimen.tool_bar_height), menuObjects );

Set menu with button, which will open ContextMenuDialogFragment.

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
                mMenuDialogFragment.show(fragmentManager, "DropDownMenuFragment");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

Implement ContextMenuDialogFragment.ItemClickListener interface with onItemClick       method.
public class MainActivity extends ActionBarActivity implements ContextMenuDialogFragment.ItemClickListener
…
 @Override
    public void onItemClick(View clickedView, int position) {
//Do something here
    }

PS: 
For better experience menu item size should be equal to ActionBar height.
newInstance of ContextMenuDialogFragment can receive animationDelay - delay in millis after fragment opening and before closing, which will make animation smoother on slow devices,
and animationDuration - duration of every piece of animation in millis. 
