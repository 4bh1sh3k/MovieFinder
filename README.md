I made this project as a quick POC. This progress was done in roughly 8 hours and there are many improvements to consider.

Next steps to consider:

1. I created two recyclerview adapter: one for search result and the other for favorites. I think it should be possible to create a generic adapter for these 2 cases.
2. The app doesn't save search results while rotating. Technically we need to persist the Movie objects in onSave/RestoreState. However they are also used in Room so I didn't want to make them Parcelable. The class can probably be refactored in a way to separate out the parceling logic for data class. There were some other options: 1) I could've used ViewModel from Jetpack libraries 2) I could've put a scope in the viewmodel class so Dagger wouldn't recreate the class.  
3. Overall the UI can be improved.