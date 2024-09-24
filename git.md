# Git

## Merge vs Rebase

TL;DR:

- `git pull --rebase` instead of `git pull` to avoid merge commits that result
from `git pull`.
- `git rebase -i @{u}` before `git push`.
- on long-lived feature branch, `git merge master` to make feature compatible
with latest master.
- on main branch, `git merge --no-ff feature-branch` to ship a feature.

### Interactively rebasing commits before pushing

This command rewrites only the local commits which you are about to push. It
gives one a chance to perform housekeeping before sharing your changes, such as
squashing related commits together and rewriting commit messages.

```bash
git rebase -i @{u}
```

The `u` stands for upstream and it resolves to the latest commit on this branch
on the remote.

### Integrate changes from master into a feature banch with merge

Working on a long-lived feature branch, it pays off sometimes to merge in the
master branch to verify that they are compatible and to get the latest bug
fixes.

Rebasing is also an options but could lead to shortcomings:

- Rewriting history on a long lived feature branch with multiple people working
on it is hard.
- Merge conflicts can be easier to deal with by merging instead of rebasing
(smaller and more conflicts with rebase).

## Commands

- Remove hunks interactively from the worktree

```bash
# All files
git checkout -p
# Only filename
git checkout -p filename
```

- Add hunks interactively from the worktree

```bash
git add -p
```

- Squash last 5 commits

```bash
git rebase -i HEAD~5
```

- Preview git stash

```bash
git stash show -p
```

- Stash files that are not in the index

```bash
git stash --keep-index
```

- Initialize git submodules

```bash
git submodule update --init --recursive
```

- Reset the git history of a repo

```bash
## Remove the history from
rm -rf .git

## recreate the repos from the current content only
git init
git add .
git commit -m "Initial commit"

## push to the github remote repos ensuring you overwrite history
git remote add origin git@github.com:<YOUR ACCOUNT>/<YOUR REPOS>.git
git push -u --force origin master
```

- Add signing key to git

```bash
$ gpg --list-keys --keyid-format long
$ git config --global commit.gpgsign true
$ git config --global user.signingkey <KEYID>
```

- When committing add the -S flag

```bash
git commit -S -m "your commit message"
```

- Get one file from another branch - filename.txt from feature1 branch

```bash
git checkout feature1 -- filename.txt
```

- git reset

```bash
# Same as git reset --mixed HEAD
git reset
# --soft: only commit history
# --mixed: staged snapshot and commit history
# --hard: working directory and staged snapshot and commit history
```

- git revert

```bash
# Changes to be reverted BCD
A <-- B <-- C <-- D
# Solution 1
git revert --no-commit D
git revert --no-commit C
git revert --no-commit B
git commit -m "the commit message"
# Solution 2 - assuming the branch is `feature`
# The beginning of the range is exclusive
git revert feature~3..feature
# Solution 3
git revert --no-commit HEAD~3..
```

- git log - 3 most recent commits

```bash
git log -3
```

- git log - by date

```bash
git log --after="2019-1-1"
git log --after="yesterday"
git log --after="2014-7-1" --before="2014-7-4"
```

- git log - by author

```bash
git log --author="Chouffe"
```

- git log - by message

```bash
git log --grep="JRA-224:"
```

- git log - by file

```bash
git log -- foo.py bar.py
```

- git log - by content

```bash
git log -S"Hello, World!"
```

- Browse git stashes

```bash
tig stash
```

- List all files that were changed in the branch

```bash
git diff --name-only "$(git merge-base master HEAD)"
```

- Find the 10 files taking up the most space in the repo

```bash
git ls-files | xargs ls -l | sort -nrk5 | head -n 10
```

## Hooks

- Prevent from pushing to master

```bash
#!/bin/bash
# .git/hooks/pre-push
# chmod +x .git/hooks/pre-push

protected_branch='master'
current_branch=$(git symbolic-ref HEAD | sed -e 's,.*/\(.*\),\1,')

if [ $protected_branch = $current_branch ]
then
    read -p "You're about to push master, is that what you intended? [y|n] " -n 1 -r < /dev/tty
    echo
    if echo $REPLY | grep -E '^[Yy]$' > /dev/null
    then
        exit 0 # push will execute
    fi
    exit 1 # push will not execute
else
    exit 0 # push will execute
fi
```

## Terms

### Repository

A repository is a collection of commits, each of which is an archive of what
the project’s working tree looked like at a past date, whether on your machine
or someone else’s. It also defines HEAD (see below), which identifies the
branch or commit the current working tree stemmed from. Lastly, it contains a
set of branches and tags, to identify certain commits by name.

### Index

Unlike other, similar tools you may have used, Git does not commit changes
directly from the working tree into the repository. Instead, changes are first
registered in something called the index. Think of it as a way of “confirming”
your changes, one by one, before doing a commit (which records all your
approved changes at once). Some find it helpful to call it instead as the
“staging area”, instead of the index.

### Working tree

A working tree is any directory on your filesystem which has a repository
associated with it (typically indicated by the presence of a sub-directory
within it named .git.). It includes all the files and sub-directories in that
directory.

### Commit

A commit is a snapshot of your working tree at some point in time. The state of
HEAD (see below) at the time your commit is made becomes that commit’s parent.
This is what creates the notion of a “revision history”.

### Branch

A branch is just a name for a commit (and much more will be said about commits
in a moment), also called a reference. It’s the parentage of a commit which
defines its history, and thus the typical notion of a “branch of development”.

### Tag

A tag is also a name for a commit, similar to a branch, except that it always
names the same commit, and can have its own description text.

### Master

The mainline of development in most repositories is done on a branch called
“**master**”. Although this is a typical default, it is in no way special.

### HEAD

HEAD is used by your repository to define what is currently checked out.

## Internals

Git is a Content-addressabe filesystem: A simple key-value store.

### Git Internals Resources

* [Git Internals Git Objects](https://git-scm.com/book/en/v2/Git-Internals-Git-Objects)
* [Git from the inside out](https://maryrosecook.com/blog/post/git-from-the-inside-out)
* [Git from the bottom up](https://jwiegley.github.io/git-from-the-bottom-up/)
* [Git clone in Haskell](http://stefan.saasen.me/articles/git-clone-in-haskell-from-the-bottom-up/)
* [I haskell a Git](http://vaibhavsagar.com/blog/2017/08/13/i-haskell-a-git/)

### Git Objects

Corresponds to inodes or file contents.

### Tree Objects

It solves the problem of storing the filename and group files together.
A single tree object contains one or more tree entries, each of which contains
a sha-1 pointer to a blob or a subtree with its associated mode, type and
filename.

Eg.

```txt
100644 blob a906cb2a4a904a152e80877d4088654daad0c859      README
100644 blob 8f94139338f9404f26296befa88755fc2598c289      Rakefile
040000 tree 99f1a6d12cb4b6f19c8655fca46c3ecf317074e0      lib
```

### Commit Objects

It specifies the top level tree for the snapshot of the project, author and
commiter information.

Eg.

```txt
tree d8329fc1cc938780ffdd9f94e0d364e0ea74f579
author Scott Chacon <schacon@gmail.com> 1243040974 -0700
committer Scott Chacon <schacon@gmail.com> 1243040974 -0700
```

### Git Internals Commands

Hash any content

```bash
git hash-object
```

Hash from --stdin and store it with -w

```bash
echo 'hello world' | git hash-object -w --stdin
```

Hash from filepath and store it with -w

```bash
git hash-object -w test.txt
```

Cat pretty printed object:

```bash
git cat-file -p <sha1>
```

Object directory is located at `.git/objects`

Return object type of any object

```bash
git cat-file -t <sha1>
```

## Extras - git-extras

- sed in git grep

```bash
git sed old-value new-value
```

- Undo the last N commit - moving them to the staging area

```bash
git undo    # Undo the last commit and add changes to staging
git undo N  # Undo N commits
```

- Completely remove a file from the repository

```bash
git obliterate filename
```

- Does a hard reset and deletes all untracked files from the working directory, including those in .gitignore.

```bash
git clear
```

- Add an entry into gitignore

```bash
git gitignore DS_Store
```

- Diff with current merged master

```bash
git diff `git merge-base master HEAD`
```

- Prevent pushing to master

```bash
git config --local branch.master.pushRemote no_push
```

## Git LFS

- Install Git LFS

```bash
sudo apt-get install git-lfs
git lfs install
```

- Clone repo without LFS objects

```bash
GIT_LFS_SKIP_SMUDGE=1 git clone REPO_URL
```

- List lfs files in a repo

```bash
git lfs ls-files
```

- Invalidate the local LFS cache

```bash
git lfs prune
```

- Migrate a file to LFS

```bash
git lfs migrate import --yes --no-rewrite "<your-file>"
```

## Resources

- [Merge vs Rebase](https://mislav.net/2013/02/merge-vs-rebase/)
- [Git Reset Tutorial](https://www.atlassian.com/git/tutorials/undoing-changes/git-reset)
- [Tutorial: Learn how to undo changes in Git](https://www.atlassian.com/git/tutorials/learn-undoing-changes-with-bitbucket)
- [Tutorial: Rewriting history](https://www.atlassian.com/git/tutorials/rewriting-history)
- [Tutorial: Rebase](https://www.atlassian.com/git/tutorials/rewriting-history/git-rebase)
- [Tutorial: Reflogs](https://www.atlassian.com/git/tutorials/rewriting-history/git-reflog)
- [Git Large File Storage](https://docs.gitlab.com/ee/topics/git/lfs/)
- [Git LFS official documentation](https://git-lfs.github.com/)
- [Git LFS: Why and How to use?](https://mydeveloperplanet.com/2018/10/31/git-lfs-why-and-how-to-use/)
- [Stack Overflow: Sparse Checkout](https://stackoverflow.com/questions/600079/how-do-i-clone-a-subdirectory-only-of-a-git-repository/52269934#52269934)
- [A few git tips you didn't know about](https://mislav.net/2010/07/git-tips/)
