# Build using
# NIXOS_CONFIG="`pwd`/configuration.nix"
# NIX_PATH="nixpkgs=/path/to/nixpkgs/repository/at/revision/32b7b00:$NIX_PATH"
# export NIXOS_CONFIG NIX_PATH
# nixos-rebuild build-vm

# Run with exported shared directory (ideally project root)
# Note, using VM clock, not real clock
# log-in with name: "guest" and password: "vm"
# SHARED_DIR=/home/kr2/Dokumentumok/PartII-Project/part-II-project/
# export QEMU_KERNEL_PARAMS SHARED_DIR
# ./result/bin/run-vmhost-vm -rtc 'base=utc,clock=vm'

# Then /tmp/shared is mounted to wherever you have set $SHARED_DIR

# Can build the project with (if you are in project-root and have the file jasmin-2.4/jasmin.jar)
# awk 'BEGIN{RS="```"; FS="\n"; OFS="\n"} /^bash/{$1=""; print $0}' < BUILD.md | sh
# which just reads out the commands to execute from BUILD.md

# Also remeber to put results into a tmpfs mount, I have used
# mkdir /tmp/ram
# sudo mount -t tmpfs tmpfs /tmp/ram
# then move them onto the shared store when they are done
# and prefix time-dependent tests with `sudo nice -n -20` so that they
# are interrupted less often

{ config, pkgs, ... }:
{ environment.systemPackages = with pkgs; [
    wget
    jdk
    maven
  ];

  fileSystems."/".label = "vmdisk";

  networking.hostName = "vmhost";
  users.extraUsers.guest = {
    isNormalUser = true;
    uid = 1000;
    password = "vm";
    group = "wheel";
  };
  users.extraUsers.root = {
    password = "root";
  };
  security.sudo = {
    enable = true;
    wheelNeedsPassword = false;
  };
  system.stateVersion = "16.09";
}
