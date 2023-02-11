# git-starter-core

This repository contains an opinionated configuration of a basic Git-based project with the following goals in mind:

- Editor/IDE agnostic
- Platform agnostic
- Programming language agnostic

## The [`.gitattributes`](./.gitattributes) file

This presence of this file overrides the global `core.autocrlf` Git configuration. This ensures consistent behavior for all users, regardless of their Git settings and environment.  

**Why?**  

Windows is the ONLY operating system that uses `CRLF` [line endings](https://en.wikipedia.org/wiki/Newline); macOS and Linux both use `LF`.  

We are currently well into the 21st millennium and pretty much every IDE and text editor in existence on Windows 7 and newer supports changing default line endings to `LF`.  

`core.autocrlf` attempts to solve the problem of Git repositories having contributors from both `CRLF`-land and `RF`-land, but it can lead to inconsistent behavior when using `git`.  

The solution is to not be lazy. If you're on Windows, turn off `core.autocrlf` or use a `.gitattributes` file (see below), and configure your IDE/editor to use `LF` rather than `CRLF`. `core.autocrlf` really doesn't need to exist when Windows applications are capable of using `LF` files.  

> You'll want to make sure files you're committing are covered by the [`.gitattributes`](./.gitattributes) file.  
> 
> **General Guidance**:  If it's a text-based file, and it's not a native Windows file (e.g., `.bat`, `.ps`, `.ps1`), default to LF.

## Using this template

1. Create a new directory for your project
2. Apply `git config --local core.autocrlf false` in `${PROJECT_DIR}`
3. Copy the [`.vscode`](./.vscode) directory and its contents to `${PROJECT_DIR}/`
4. Copy [`.gitignore`](./.gitignore) to `${PROJECT_DIR}/`
5. Copy [`.gitattributes`](./.gitattributes) to `${PROJECT_DIR}/`
6. If on Windows: Ensure your IDE/editor is configured to use `LF` by default
7. Update the [`.gitignore`](./.gitignore) file for your needs (i.e., apply programming language or framework entries)
