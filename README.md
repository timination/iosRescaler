# iosRescaler
Rescales screenshots to required dimensions for app store submission.

When preparing new ios apps for submission, I take screenshots on my phone and ipad but these are the wrong scale for the app store specifications so instead of manually resizing each image individually in photoshop, which is what I was originally doing, I wrote this to do it for me. I may implement a GUI at a later stage. 

iosRescaler takes a path to a directory which should contain two folders - 'iphone' and 'ipad', iphone screenshots in the iphone folder, ipad screenshots in the ipad folder. Every image within each folder will be rescaled to the dimensions listed below next to the selected option. 

To compile: javac iosRescaler.java

To use: _java_ _iosRescaler_ _pathToImage_ _option_

     *     iphone   x   ipad
     *
     * 1. portrait  x portrait  (1242x2208) x (2048x2732)
     * 2. portrait  x landscape (1242x2208) x (2732x2048)
     * 3. landscape x landscape (2208x1242) x (2732x2048)
     * 4. landscape x portrait  (2208x1242) x (2048x2732)
     * There should be two folders in the given path, 'iphone' and 'ipad', which should contain the respective images to be rescaled. These *WILL* be overwritten.
