# VIM

## The vim way

* Dot formula: one keystroke to move, on keystroke to execute

## Overview

* Start vim without loading the vimrc
```
vim -u NONE -N
```
* Start with minimal built-in vim plugings
```
# vim file to source vim/essential.vim
set nocompatible
filetype plugin on

# Source with
vim -u vim/essential.vim
```

### Normal Mode

* Jump to last edited position: `g;`

### Insert Mode

* `<C-h>` Delete back one character
* `<C-w>` Delete back one word
* `<C-u>` Delete back to start of the line

### Insert Normal mode

One needs to run a normal command when in insert mode and get back to insert mode right after.
When in insert mode, type `<C-o>`.

* Center buffer when in insert mode: `<C-o>zz`
* Yank a pasted word from insert mode: `<C-r>0`

### Yank

* Previous yanked words are rotated into registered `0-9`
```
# Paste last yanked thing
Normal mode: 0p
Insert Mode: <C-r>0
```

### Substitutions

* When searching, `\n` is a newline
* When replacing, `\r` is a newline

* Perform substitution
```
:s/target/replacement
```
* Repeat
```
&
```
* Reverse
```
u
```
* Confirm a substitution
```
:s/target/replacement/gc
```

### `<C-r>` Mechanism

#### Normal Mode

Redo

#### Insert or Command mode

One can continue with a numbered or named registered:

* `a - z` the named registers
* `"` the unnamed register, containing the text of the last delete or yank
* `%` the current file name
* `#` the alternate file name
* `*` the clipboard contents (X11: primary selection)
* `+` the clipboard contents
* `/` the last search pattern
* `:` the last command-line
* `.` the last inserted text
* `-` the last small (less than a line) delete
* `=5*5` insert 25 into text (mini-calculator)

To list registers: `:reg` or `:registers` or `:di`


## Fugitive: GDiff

### Resources

* [VimCast](http://vimcasts.org/episodes/fugitive-vim-resolving-merge-conflicts-with-vimdiff/)

### Conventions and Naming

* Target (HEAD): `//2` -> Left side
* Merge: `//3` -> Right side
* Working copy: middle

### Commands

* Vertical split 3 way merge `:Gvdiff`
* Horizontal split 3 way merge `:Gsdiff`
* **Changeset Navigation**
  * `]c`: next changeset
  * `[c`: previous changeset
* Select from target: On working copy run `:diffget //2`
* Select from merge: On working copy run `:diffget //3`
* Put from target to working copy: `diffput $working-copy-identifier`
* Chain commands: `diffput demo | diffupdate`
* Mark the patch complete: `diffupdate`
* When all conflicts have been resolved, close all but working copy with `:only`
* Stage the working copy with `:Gwrite`
* Keep Target / Merge entirely and adds it to the index: `Gwrite!` - Warning: It will lose the working copy

## Vim Lists

### Jump List

| Action        | Command    |
|---------------|------------|
| Show jump list| `:ju[mps]` |
| Previous jump | `<C-o>`    |
| Next jump     | `<C-i>`    |

### Change List

| Action                           | Command    |
|----------------------------------|------------|
| Show change list                 | `:changes` |
| Jump to location of older change | `g;`       |
| Jump to location of newer change | `g,`       |


### Quickfix List

| Action              | Command              |
|---------------------|----------------------|
| Open quickfix list  | `:cope[n]`           |
| Close quickfix list | `:ccl[ose]`          |
| Previous location   | `:cp[revious] / [q*` |
| Next location       | `:cn[ext] / ]q*`     |
| First location      | `:cfir[st] / [Q*`    |
| Last location       | `:cla[st] / ]Q*`     |

### Location List

| Action                 | Command              |
|------------------------|----------------------|
| Open location list 	 | `:lope[n]`           |
| Close location list 	 | `:lcl[ose]`          |
| Previous location 	 | `:lp[revious] / [l*` |
| Next location 	 | `:lne[xt] / ]l*`     |
| First location 	 | `:lfir[st] / [L*`    |
| Last location 	 | `:lla[st] / ]L*`     |

### Buffer List

| Action                 | Command              |
|------------------------|----------------------|
| Show buffer list       | `:buffers / :ls`     |
| Previous buffer        | `:bp[revious] / [b*` |
| Next buffer            | `:bn[ext] / ]b*`     |
| First buffer           | `:bf[irst] / [B*`    |
| Last buffer            | `:bl[ast] / ]B*`     |

## Resources

* [Vim Lists](https://noahfrederick.com/log/a-list-of-vims-lists)
