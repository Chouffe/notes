# VIM

## Fugitive: GDiff

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
