#!/bin/perl
use 5.26.1;

my @namen = ();
my @afkortingen = ();
my @nummer = ();
my @nummer_afkortingen = ();
my @gewicht = ();

my $filename = 'elementen.txt';
my $noerror = open FILE, '<', $filename;
if($noerror) {
	while (<FILE>) {
		next if length($_) eq 1;
		my $stripped = s/\s*//g;
		my @e = split '\|', $_;
		push @namen, "Wat is de naam van het element $e[1]? $e[0]";
		push @afkortingen, "Wat is de afkorting van $e[0]? $e[1]";
		push @nummer, "Welk element nummer heeft $e[0]? $e[2]";
		push @nummer_afkortingen, "Wat is de afkorting van element #$e[2]? $e[1]";
		push @gewicht, "Wat is de AMU van $e[1]? $e[3]";
	}
	close FILE;
} else {
	say "error message: $!";
	die "unable to open file $filename";
}

say for(@namen);
say for(@afkortingen);
say for(@nummer);
say for(@nummer_afkortingen);
say for(@gewicht);

