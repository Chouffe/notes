# Git

## Commands

* Remove hunks interactively from the worktree
```
# All files
git checkout -p
# Only filename
git checkout -p filename
```
* Add hunks interactively from the worktree
```
git add -p
```
* Squash last 5 commits
```
git rebase -i HEAD~5
```
* Preview git stash
```
git stash show -p
```
* Stash files that are not in the index
```
git stash --keep-index
```
* Initialize git submodules
```
git submodule update --init --recursive
```
* Reset the git history of a repo
```
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
* Add signing key to git
```
$ gpg --list-keys --keyid-format long
$ git config --global commit.gpgsign true
$ git config --global user.signingkey <KEYID>
```
* When committing add the -S flag
```
git commit -S -m "your commit message"
```
* Get one file from another branch - filename.txt from feature1 branch
```
git checkout feature1 -- filename.txt
```
* git reset
```
# Same as git reset --mixed HEAD
git reset
# --soft: only commit history
# --mixed: staged snapshot and commit history
# --hard: working directory and staged snapshot and commit history
```
* git revert
```
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
* git log - 3 most recent commits
```
git log -3
```
* git log - by date
```
git log --after="2019-1-1"
git log --after="yesterday"
git log --after="2014-7-1" --before="2014-7-4"
```
* git log - by author
```
git log --author="Chouffe"
```
* git log - by message
```
git log --grep="JRA-224:"
```
* git log - by file
```
git log -- foo.py bar.py
```
* git log - by content
```
git log -S"Hello, World!"
```
* Browse git stashes
```
tig stash
```
* List all files that were changed in the branch
```
git diff --name-only "$(git merge-base master HEAD)"
```

## Hooks

* Prevent from pushing to master
```
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

A repository is a collection of commits, each of which is an archive of what the project’s working tree looked like at a past date, whether on your machine or someone else’s. It also defines HEAD (see below), which identifies the branch or commit the current working tree stemmed from. Lastly, it contains a set of branches and tags, to identify certain commits by name.

### Index

Unlike other, similar tools you may have used, Git does not commit changes directly from the working tree into the repository. Instead, changes are first registered in something called the index. Think of it as a way of “confirming” your changes, one by one, before doing a commit (which records all your approved changes at once). Some find it helpful to call it instead as the “staging area”, instead of the index.

### Working tree

A working tree is any directory on your filesystem which has a repository associated with it (typically indicated by the presence of a sub-directory within it named .git.). It includes all the files and sub-directories in that directory.

### Commit

A commit is a snapshot of your working tree at some point in time. The state of HEAD (see below) at the time your commit is made becomes that commit’s parent. This is what creates the notion of a “revision history”.

### Branch

A branch is just a name for a commit (and much more will be said about commits in a moment), also called a reference. It’s the parentage of a commit which defines its history, and thus the typical notion of a “branch of development”.

### Tag

A tag is also a name for a commit, similar to a branch, except that it always names the same commit, and can have its own description text.

### Master

The mainline of development in most repositories is done on a branch called “**master**”. Although this is a typical default, it is in no way special.

### HEAD

HEAD is used by your repository to define what is currently checked out.

## Internals

Git is a Content-addressabe filesystem: A simple key-value store.

### Resources

- [Git Internals Git Objects](https://git-scm.com/book/en/v2/Git-Internals-Git-Objects)
- [Git from the inside out](https://maryrosecook.com/blog/post/git-from-the-inside-out)
- [Git from the bottom up](https://jwiegley.github.io/git-from-the-bottom-up/)
- [Git clone in Haskell](http://stefan.saasen.me/articles/git-clone-in-haskell-from-the-bottom-up/)
- [I haskell a Git](http://vaibhavsagar.com/blog/2017/08/13/i-haskell-a-git/)

### Git Objects

Corresponds to inodes or file contents.

### Tree Objects

It solves the problem of storing the filename and group files together.
A single tree object contains one or more tree entries, each of which contains a sha-1 pointer to a blob or a subtree with its associated mode, type and filename.

Eg.
```
100644 blob a906cb2a4a904a152e80877d4088654daad0c859      README
100644 blob 8f94139338f9404f26296befa88755fc2598c289      Rakefile
040000 tree 99f1a6d12cb4b6f19c8655fca46c3ecf317074e0      lib
```

### Commit Objects

It specifies the top level tree for the snapshot of the project, author and commiter information.

Eg.
```
tree d8329fc1cc938780ffdd9f94e0d364e0ea74f579
author Scott Chacon <schacon@gmail.com> 1243040974 -0700
committer Scott Chacon <schacon@gmail.com> 1243040974 -0700
```


### Commands

Hash any content
```
git hash-object
```

Hash from --stdin and store it with -w
```
echo 'hello world' | git hash-object -w --stdin
```

Hash from filepath and store it with -w
```
git hash-object -w test.txt
```

Cat pretty printed object
```
git cat-file -p <sha1>
```

Object directory is located at `.git/objects`

Return object type of any object
```
git cat-file -t <sha1>
```

## Extras - git-extras

* sed in git grep
```
git sed old-value new-value
```
* Undo the last N commit - moving them to the staging area
```
git undo    # Undo the last commit and add changes to staging
git undo N  # Undo N commits
```
* Completely remove a file from the repository
```
git obliterate filename
```
* Does a hard reset and deletes all untracked files from the working directory, including those in .gitignore.
```
git clear
```
* Add an entry into gitignore
```
git gitignore DS_Store
```
* Diff with current merged master
```
git diff `git merge-base master HEAD`
```
* Prevent pushing to master
```
git config --local branch.master.pushRemote no_push
```

## Resources

* [Git Reset Tutorial](https://www.atlassian.com/git/tutorials/undoing-changes/git-reset)
* [Tutorial: Learn how to undo changes in Git](https://www.atlassian.com/git/tutorials/learn-undoing-changes-with-bitbucket)
* [Tutorial: Rewriting history](https://www.atlassian.com/git/tutorials/rewriting-history)
* [Tutorial: Rebase](https://www.atlassian.com/git/tutorials/rewriting-history/git-rebase)
* [Tutorial: Reflogs](https://www.atlassian.com/git/tutorials/rewriting-history/git-reflog)
