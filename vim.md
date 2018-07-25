# VIM

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
