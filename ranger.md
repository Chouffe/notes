# Ranger: File Browsing

## Basic commands

### Actions

* `Space`: Select - unselect file or directory
* `Del`: delete specific file or directory
* `o`: change sort order
* `y`: copy/yank file/dir
* `p`: paste what is yanked
* `dd`: delete (it is in the yank register)
* `dD`: delete!!
* `A`: rename file/folder

### Tab management

* `-n`: open new tab
* `ab`: switch to next tab
* `-w`: close tab
* `t/gT`: change tab
* `q`: undo quit, restore tab
* `lt-1/Alt-9`: switch to tab N

### Navigation

* `h,i,j,k`: navigation filesystem
* `gh`: go to home directory
* `go`: go to root directory
* `f`: find filename
* `/`: search filemame
* `n/N`: next/previous
* `q`: back from pager/quit pager

### Help

* `1?`: keybinding help
* `2?`: command help

### On files

* `Enter`: open with default program
* `i`: open with pager (less in general)

## Advanced section

### Tmux

* `w`: open in new tmux window
* `f`: split horizontal in tmux pane
* `v`: split verical in tmux pane

### Marks
* `m<Char>`: set mark 'Char'
* `'<Char>`: jump to mark 'Char'
